package modisefileupload.java.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import modisefileupload.java.domain.User;
import modisefileupload.java.service.UserService;

@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	private Path path;

	@RequestMapping(value = "/usersList")
	public ModelAndView  listUsers() {
		List<User> users = userService.listAllUsers();
		LOGGER.debug("------------------------>" + users.size());
		return new ModelAndView("/usersList", "users", users);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		User user = new User();
		user.setAge(0);
		model.addAttribute(user);
		return "index";
	}

	@RequestMapping(value = "/index/saveUser", method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			return "/index";
		}
		
		//save user to database
		userService.saveUser(user);

		// get the provided image from the form
		MultipartFile userImage = user.getUserImage();

		// get root directory to store the image
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		// change any provided image type to png
		// path = Paths.get(rootDirectory + "/WEB-INF/resources/images" +
		// product.getProductId() + ".png");
		path = Paths.get("/home/mrmodise/SpringWorkspace/modise-file-upload/src/main/webapp/WEB-INF/resources/uploaded-images/"
				+ user.getUserId() + ".png");

		// check whether image exists or not
		if (userImage != null && !userImage.isEmpty()) {
			try {
				// convert the image type to png
				userImage.transferTo(new File(path.toString()));
			} catch (IllegalStateException | IOException e) {
				// oops! something did not work as expected
				e.printStackTrace();
				throw new RuntimeException("Saving User image was not successful", e);
			}
		}

		return "redirect:/?success=1";
	}
	
	/**
	 * deleting the user information requires that we delete the image they uploaded
	 * the code below takes the preceding thoughts into care
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/index/deleteUser/{id}")
	public String deleteUser(@PathVariable long id, HttpServletRequest request){
		// get root directory to store the image
				String rootDirectory = request.getSession().getServletContext().getRealPath("/");

				// change any provided image type to png
				// path = Paths.get(rootDirectory + "/WEB-INF/resources/images" +
				// product.getProductId() + ".png");
				path = Paths
						.get("/home/mrmodise/SpringWorkspace/modise-file-upload/src/main/webapp/WEB-INF/resources/uploaded-images/" + id + ".png");
			
				if(Files.exists(path)){
					try {
						Files.delete(path);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				userService.deleteUser(id);
		return "redirect:/usersList?delete=1";
	}

	
	
}
