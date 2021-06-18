package org.supercall.plugins;

import org.supercall.domainModel.SelectorDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kira on 16/8/2.
 */
public class CommonPlugin {
    public List<SelectorDataSource> showOrHideDataSource(){
        List<SelectorDataSource> selectorDataSources=new ArrayList<>();
        selectorDataSources.add(new SelectorDataSource("1","显示"));
        selectorDataSources.add(new SelectorDataSource("0","隐藏"));
        return selectorDataSources;
    }
}
