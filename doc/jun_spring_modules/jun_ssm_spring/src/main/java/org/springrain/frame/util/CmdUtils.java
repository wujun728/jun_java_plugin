package org.springrain.frame.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行命令行的工具类,需要提前设定执行权限
 * 
 * @author caomei
 *
 */
public class CmdUtils {
    private static final Logger logger = LoggerFactory.getLogger(CmdUtils.class);

    private CmdUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    public static StringBuilder execCmd(String commandStr) {
        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            return execWindowsCmd(commandStr);
        } else if (os.startsWith("Linux")) {
            return execLinuxCmd(commandStr);
        } else {
            return null;
        }
    }

    private static StringBuilder execWindowsCmd(String commandStr) {
        List<String> cmds = new ArrayList<>();
        cmds.add("cmd.exe");
        cmds.add("/c");
        cmds.add(commandStr);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        return execCmd(pb);
    }

    private static StringBuilder execLinuxCmd(String commandStr) {
        List<String> cmds = new ArrayList<>();
        cmds.add("sh");
        cmds.add("-c");
        cmds.add(commandStr);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        return execCmd(pb);
    }

    /**
     * 执行命令行
     * 
     * @param cmds
     * @return
     */
    public static StringBuilder execCmd(ProcessBuilder pb) {

        BufferedReader br = null;
        try {

            Process p = pb.start();
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * public static void main(String[] args) { StringBuilder execCmd =
     * execCmd("ipconfig /all"); System.out.println(execCmd.toString()); }
     */
}
