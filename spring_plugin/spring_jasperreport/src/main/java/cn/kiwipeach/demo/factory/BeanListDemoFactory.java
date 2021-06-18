/*
 * Copyright 2017 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.factory;


import cn.kiwipeach.demo.domain.City;
import cn.kiwipeach.demo.domain.Province;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * from:https://stackoverflow.com/questions/12209300/jrbeancollectiondatasource-how-to-show-data-from-the-java-util-list-from-javabe
 * Create Date: 2017/12/26
 * Description: BeanWithList的工厂类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class BeanListDemoFactory {

    public static Collection<Province> createBeanListCollection() {
        Collection<Province> provinceCollection = new ArrayList<Province>();
        for (int i = 0; i < 16; i++) {
            List<City> cityList = new ArrayList<City>();
            for (int j = 0; j < 4; j++) {
                cityList.add(new City("城市编号:100" + j, "城市名称:县区" + j));
            }
            Province province = new Province(cityList, "省份编号:36073500" + i);
            provinceCollection.add(province);
        }
        return provinceCollection;
    }

    public static void main(String[] args) {
        Collection<Province> beanListCollection = createBeanListCollection();
        System.out.println("size is " +beanListCollection.size());
        System.out.println(beanListCollection);
    }
}
