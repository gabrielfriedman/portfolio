package com.techelevator.npgeek.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.npgeek.model.park.Park;
import com.techelevator.npgeek.model.park.ParkDAO;
import com.techelevator.npgeek.model.survey.Survey;
import com.techelevator.npgeek.model.survey.SurveyDAO;
import com.techelevator.npgeek.model.user.User;
import com.techelevator.npgeek.model.user.UserDAO;
import com.techelevator.npgeek.model.weather.Weather;
import com.techelevator.npgeek.model.weather.WeatherDAO;

@Controller
@Scope("session")
public class SiteController {

	@Autowired
	public ParkDAO theParks;

	@Autowired
	public WeatherDAO theWeather;

	@Autowired
	public SurveyDAO theSurvey;

	@Autowired
	private AuthProvider auth;

	@Autowired
	private UserDAO aUser;

	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String displayRegister(ModelMap theModel) {
		if (!theModel.containsAttribute("User")) {
			theModel.addAttribute("User", new User());
		}
		return "register";
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST) // @Valid = validate
	public String register(@Valid @ModelAttribute("user") User user, // Server should validate using the "user in
																		// ModelMap and User in POJO
			BindingResult result, // Result of validation is placed in BindingResult object
			RedirectAttributes flash) { // Give me a flashMap so I can send the validation result and data back if there
										// is an error
		if (result.hasErrors()) { // If there are any error...
			flash.addFlashAttribute("User", user); // Send the data entered from the user back to the view
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "User", result); // Send the validation result back
																						// to the view
			flash.addFlashAttribute("message", "Please fix the following errors:"); // Send a general message to be
																					// displayed concerning the errors
			return "redirect:/"; // If no registration errors - run the register method
		}
		auth.register(user.getUsername(), user.getPassword(), user.getEmail(), user.getPasswordHint());
		return "redirect:/login";
	}

	@RequestMapping(path = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword(ModelMap modelHolder) {
		return "forgotPassword";
	}

	@RequestMapping(path = "/forgotPassword", method = RequestMethod.POST)
	public String giveMeAHint(@RequestParam String username, ModelMap modelHolder) {
		User user = aUser.getHint(username);
		modelHolder.addAttribute("hint", user);
		return "redirect:/forgotPassword";
	}

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login(ModelMap modelHolder) {
		User user = auth.getCurrentUser();
		modelHolder.addAttribute("user", user);
		return "login";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes flash) {
		if (auth.signIn(username, password)) {
			return "redirect:/login";
		} else {
			flash.addFlashAttribute("message", "Login Invalid");
			return "redirect:/login";
		}
	}
	
	@RequestMapping(path = "/userSettings", method = RequestMethod.GET)
	public String settings(ModelMap modelHolder) {
		if (!auth.isLoggedIn()) {
			return "redirect:/register";
		}
		User user = auth.getCurrentUser();
		modelHolder.addAttribute("user", user);
		return "userSettings";
	}
	
	@RequestMapping(path = "/userSettings", method = RequestMethod.POST)
	public String passwordChange(@RequestParam String existingPassword, @RequestParam String newPassword, RedirectAttributes flash) {
		if (auth.changePassword(existingPassword, newPassword)) {
			return "redirect:/homepage";
		} else {
			flash.addFlashAttribute("message", "Invalid Information");
			return "redirect:/userSettings";
		}
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String displayHomePage(HttpSession sharedWithView, ModelMap parkMap) {
		if (!auth.isLoggedIn()) {
			return "redirect:/register";
		}
		parkMap.put("user", auth.getCurrentUser());
		List<Park> listOfParks = theParks.getAllParks();
		sharedWithView.setAttribute("allParks", listOfParks);
		return "homepage";
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public String logoff(HttpSession sharedWithView, ModelMap parkMap) {
		auth.logOff();
		return "redirect:/";
	}

	@RequestMapping("/favorites")
	public String displayFavoriteParks(HttpSession sharedWithView) {
		if (!auth.isLoggedIn()) {
			return "redirect:/register";
		}
		List<Park> parkList = theParks.getFavorites();
		sharedWithView.setAttribute("favParks", parkList);
		return "favorites";
	}

	@RequestMapping(path = "/details", method = RequestMethod.GET)
	public String displayDetailsPage(@RequestParam String selectedPark, HttpSession sharedWithView, ModelMap parkMap) {
		if (!auth.isLoggedIn()) {
			return "redirect:/register";
		}
		String tempChoice = (String) sharedWithView.getAttribute("tempChoice");
		if (tempChoice == null) {
			tempChoice = "Fahrenheit";
			sharedWithView.setAttribute("tempChoice", tempChoice);
		}
		Park theSelectedPark = theParks.getParkInformation(selectedPark);
		List<Weather> parkWeather = theWeather.fiveDayForecast(theSelectedPark.getCode(), tempChoice);
		sharedWithView.setAttribute("aPark", theSelectedPark);
		sharedWithView.setAttribute("fiveDay", parkWeather);
		@SuppressWarnings("unchecked")
		List<Park> listOfParks = (List<Park>) sharedWithView.getAttribute("allParks");
		if (listOfParks == null) {
			listOfParks = theParks.getAllParks();
			sharedWithView.setAttribute("allParks", listOfParks);
		}

		return "details";
	}

	@RequestMapping(path = "/details", method = RequestMethod.POST)
	public String updateDetailsPage(@RequestParam String selectedPark, @RequestParam String tempChoice,
			HttpSession sharedWithView, ModelMap parkMap) {
		sharedWithView.setAttribute("tempChoice", tempChoice);

		return "redirect:/details?selectedPark=" + selectedPark;
	}

	@RequestMapping(path = "/survey", method = RequestMethod.GET)
	public String displaySurveyForm(Model parkModel) {
		if (!auth.isLoggedIn()) {
			return "redirect:/register";
		}
		if (!parkModel.containsAttribute("Survey")) {
			parkModel.addAttribute("Survey", new Survey());
		}
		parkModel.addAttribute("Park", theParks.getAllParks());
		return "survey";
	}

	@RequestMapping(path = "/survey", method = RequestMethod.POST)
	public String submitSurvey(HttpSession sharedWithView, @RequestParam String parkCode,
			@RequestParam String emailAddress, @RequestParam String state, @RequestParam String activity,
			@Valid @ModelAttribute("Survey") Survey surveyFormValues, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "Survey", result);
			flash.addFlashAttribute("Survey", surveyFormValues);
			return "redirect:/survey";
		}

		Survey survey = new Survey();
		survey.setParkCode(parkCode);
		survey.setEmailAddress(emailAddress);
		survey.setState(state);
		survey.setActivity(activity);
		theSurvey.save(survey);
		return "redirect:/favorites";
	}

//	@RequestMapping(path="/details", method=RequestMethod.POST)
//	public String updateTemperatureDisplay(HttpSession sharedWithView) {
//		return "redirect:/details";
//	}
//	

}
