package com.jun.plugin.javase.annotation;

import java.util.List;

public class UsingBuiltInAnnotation {
	// 食物类
	class Food {
	}

	// 干草类
	class Hay extends Food {
	}

	// 动物类
	class Animal {
		Food getFood() {
			return null;
		}

		// 使用Annotation声明Deprecated方法
		@Deprecated
		void deprecatedMethod() {
		}
	}

	// 马类-继承动物类
	class Horse extends Animal {
		// 使用Annotation声明覆盖方法
		@Override
		Hay getFood() {
			return new Hay();
		}

		// 使用Annotation声明禁止警告
		@SuppressWarnings({ "deprecation", "unchecked" })
		void callDeprecatedMethod(List horseGroup) {
			Animal an = new Animal();
			an.deprecatedMethod();
			horseGroup.add(an);
		}
	}
}