package com.jun.plugin.common.base.interfaces;

@FunctionalInterface
public interface IPluginRunner {

        /**
         * Callback used to run the bean.
         * @throws Exception on error
         */
        void run() throws Exception;

}
