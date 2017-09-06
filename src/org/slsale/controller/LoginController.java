package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.User;
import org.slsale.service.function.FunctionService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends BaseController {

	private Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private UserService userService;
	@Resource
	private FunctionService functionService;
	
	@RequestMapping("/main.html")
	public ModelAndView main(HttpSession session){
		logger.debug("main=====================>");
		//menu list
		User user = this.getCurrentUser();
		List<Menu> mList = null;
		if(null != user){
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("user", user);
			//���ݵ�ǰ�û���ȡ�˵��б�
			
			//���redis�������ݣ�ֱ�Ӵ�redis����ȡ����
			mList = getFuncByCurrentUser(user.getRoleId());
			//json
			if(null != mList){
				JSONArray jsonArray = JSONArray.fromObject(mList);
				String jsonString = jsonArray.toString();
				logger.debug("jsonString====>"+jsonString);
				model.put("mList", jsonString);
				session.setAttribute(Constants.SESSION_BASE_MODEL, model);
				return new ModelAndView("main",model);
			}
			
		}
		return new ModelAndView("redirect:/");
	}

	/**
	 * ���ݵ�ǰ�û��Ľ�ɫ��ȡ���ܲ˵�
	 * @param roleId
	 * @return
	 */
	protected List<Menu> getFuncByCurrentUser(int roleId){
		List<Menu> menuList = new ArrayList<Menu>();
		Authority authority = new Authority();
		authority.setRoleId(roleId);
		
		try {
			List<Function> mList = functionService.getMainFunctionList(authority);
			if(mList !=null){
				for(Function function:mList){
					Menu menu = new Menu();
					menu.setMainMenu(function);
					function.setRoleId(roleId);
					List<Function> subList = functionService.getSubFunction(function);
					if(null !=subList){
						menu.setSubMenu(subList);
					}
					menuList.add(menu);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return menuList;
	}
	
	@RequestMapping("/login.html")
	@ResponseBody
	public Object login(HttpSession session,@RequestParam String user){  //ʹ��ResponseBodyע�⣬���ص�һ���Ǹ�����д��ǰ̨���ݽ�����
		logger.debug("========>�����̨login����");
		if(user == null || "".equals(user)){
			return "nodata";
		}else{
			JSONObject userObject = JSONObject.fromObject(user);
			User userObj = (User)JSONObject.toBean(userObject, User.class);
			try {
				if(userService.loginCodeIsExist(userObj) == 0){//�˺Ų�����
					return "nologincode";
				}else{
					User _user = userService.getLoginUser(userObj);
					if(null != _user){//��½�ɹ�
						//��ǰ�û�����session��
						session.setAttribute(Constants.SESSION_USER, _user);
						//���µ�ǰ�û���¼��lastLoginTime
						User updateLoginTimeUser = new User();
						updateLoginTimeUser.setId(_user.getId());
						updateLoginTimeUser.setLastLoginTime(new Date());
						userService.modifyUser(updateLoginTimeUser);
						updateLoginTimeUser = null;
						return "success";
					}else{//�������
						return "pwderror";
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "failed";
			}
		
		}
	}


}
