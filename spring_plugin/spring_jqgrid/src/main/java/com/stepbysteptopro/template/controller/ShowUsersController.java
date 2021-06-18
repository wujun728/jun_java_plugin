package com.stepbysteptopro.template.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stepbysteptopro.template.model.TableModel;
import com.stepbysteptopro.template.model.User;
import com.stepbysteptopro.template.service.UserService;

/**
 * Controller are getting users for users list page.
 * 
 * @author Wujun
 */
@Controller
@RequestMapping("/users")
public class ShowUsersController {

    private static final Logger logger = LoggerFactory.getLogger(ShowUsersController.class);

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * This method is run by jqGrid's Ajax request everytime 
     * when we are getting data for table.
     * 
     * @param pageNumber the requested by user page number 
     * @param rowsAmountLimit the amount rows that we want to see
     * @param sortedColumnIndex the index of column which we are going to sort by
     * @param sortOrder the sort order 
     * @param writer the writer for output
     * @throws IOException
     */
    @RequestMapping(value = "/getUsersList.do", method = RequestMethod.GET)
    public void getUsers(@RequestParam("page") int pageNumber, 
                         @RequestParam("rows") int rowsAmountLimit,
                         @RequestParam("sidx") String sortedColumnIndex, 
                         @RequestParam("sord") String sortOrder, 
                         Writer writer) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("Get users action is running...");
        }
        
        // calculate the number of rows for the query 
        int recordsAmount = userService.getUsersAmount();
        
        TableModel<User> tableModel = new TableModel<User>(pageNumber, rowsAmountLimit, recordsAmount, 
                sortedColumnIndex, sortOrder);
        
        List<User> users = userService.getUsers(tableModel);
        
        tableModel.setRows(users);
        
        String jsonObject = JSONObject.fromObject(tableModel).toString();
        
        writer.write(jsonObject);
    }

    @RequestMapping(value = "/showUsersList.do", method = RequestMethod.GET)
    public String showUsers(Model model) {
        //I set title here only for demonstration 
        //that we can pass all data which we need. 
        model.addAttribute("title", "Users list");
        
        //go to usersList.jsp page
        return "usersList";
    }
}
