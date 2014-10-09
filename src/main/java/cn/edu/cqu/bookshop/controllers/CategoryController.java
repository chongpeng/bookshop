package cn.edu.cqu.bookshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.cqu.bookshop.domains.Category;
import cn.edu.cqu.bookshop.json.FormResponse;
import cn.edu.cqu.bookshop.services.CategoryCRUD;

@Controller
public class CategoryController {
	@Autowired
	@Qualifier("categoryCRUDHibernate")
	private CategoryCRUD categoryCRUD;


	@RequestMapping(value = "/category/listAll.json")
	@ResponseBody
	public List<Category> listAll()
	{
		List<Category> categories = categoryCRUD.getAll();
		return categories;
	}

	@RequestMapping(value = "/category/addCategory.json")
	@ResponseBody
	public FormResponse addCategory(String name, String code) {
		FormResponse response = new FormResponse();
		try {
			if (categoryCRUD.getByCode(code) != null) {
				response.addError("code", "编码重复");
			}
			if (categoryCRUD.getByName(name) != null) {
				response.addError("name", "名称重复");
			}
			if (response.hasError()) {
				response.setMsg("数据异常,无法添加！");
			} else {
				Category category = new Category();
				category.setCode(code);
				category.setName(name);
				categoryCRUD.add(category);
				response.setMsg("添加成功！");

			}
		} catch (Exception e) {
			response.setMsg("系统错误，无法添加！");
			response.setSuccess(false);
			e.printStackTrace();
		}
		return response;
	}
}
