package book.basic;

public class CommandArgs {
	/**
	 * 遇到错误时，打印错误信息，并退出程序
	 * @param errmsg  错误信息
	 */
	public static void error(String errmsg) {
		System.err.println(errmsg);
		System.exit(1);
	}

	public static void main(String[] args) {
		//命令行下的帮助信息
		String usageMsg = "Usage: CommandArgs [options]\n"
				+ "Where [options] are:\n"
				+ " -help                    显示帮助信息\n"
				+ " -n <name>                设置参数的名字\n"
				+ " -v <value>               设置参数的值\n";

		String name = null;
		String value = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-n")) {
				if ((i + 1) == args.length) {
					error("Error: -n requires an argument.");
				}
				name = args[++i];
			} else if (args[i].equals("-v")){
				if ((i + 1) == args.length) {
					error("Error: -v requires an argument.");
				}
				value = args[++i];
			} else if (args[i].equalsIgnoreCase("-help")) {
				System.out.println(usageMsg);
				System.exit(0);
			} else {
				error("Error: argument not recognized: " + args[i]);
			}
		}
		System.out.println("[命令行解析结果] name: " + name + "; value: " + value);
	}
}
