package com.jun.plugin.demo;
public class Information {
	public static void main(String args[]) {
		System.out.println("java_vendor:" + System.getProperty("java.vendor"));
		System.out.println("java_vendor_url:"
				+ System.getProperty("java.vendor.url"));
		System.out.println("java_home:" + System.getProperty("java.home"));
		System.out.println("java_class_version:"
				+ System.getProperty("java.class.version"));
		System.out.println("java_class_path:"
				+ System.getProperty("java.class.path"));
		System.out.println("os_name:" + System.getProperty("os.name"));
		System.out.println("os_arch:" + System.getProperty("os.arch"));
		System.out.println("os_version:" + System.getProperty("os.version"));
		System.out.println("user_name:" + System.getProperty("user.name"));
		System.out.println("user_home:" + System.getProperty("user.home"));
		System.out.println("user_dir:" + System.getProperty("user.dir"));
		System.out.println("java_vm_specification_version:"
				+ System.getProperty("java.vm.specification.version"));
		System.out.println("java_vm_specification_vendor:"
				+ System.getProperty("java.vm.specification.vendor"));
		System.out.println("java_vm_specification_name:"
				+ System.getProperty("java.vm.specification.name"));
		System.out.println("java_vm_version:"
				+ System.getProperty("java.vm.version"));
		System.out.println("java_vm_vendor:"
				+ System.getProperty("java.vm.vendor"));
		System.out
				.println("java_vm_name:" + System.getProperty("java.vm.name"));
		System.out.println("java_ext_dirs:"
				+ System.getProperty("java.ext.dirs"));
		System.out.println("file_separator:"
				+ System.getProperty("file.separator"));
		System.out.println("path_separator:"
				+ System.getProperty("path.separator"));
		System.out.println("line_separator:"
				+ System.getProperty("line.separator"));
	}
}