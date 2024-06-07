package io.github.wujun728.common.base.compiler;

import org.springframework.util.FastByteArrayOutputStream;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;

/**
 * 内存的代码编译器，参考自 oracle jdk
 *
 * @author L.cm
 */
public class InMemoryJavaCompiler {
	/**
	 * JavaCompiler
	 */
	private static final JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();

	/**
	 * Compiles the class with the given name and source code.
	 *
	 * @param className  The name of the class
	 * @param sourceCode The source code for the class with name {@code className}
	 * @return The resulting byte code from the compilation
	 * @throws IllegalArgumentException if the compilation did not succeed
	 */
	public static byte[] compile(String className, CharSequence sourceCode) {
		MemoryJavaFileObject file = new MemoryJavaFileObject(className, sourceCode);
		CompilationTask task = getCompilationTask(file);

		if (Boolean.FALSE.equals(task.call())) {
			throw new IllegalArgumentException("Could not compile " + className + " with source code :\t" + sourceCode);
		}

		return file.getByteCode();
	}

	private static CompilationTask getCompilationTask(MemoryJavaFileObject file) {
		return COMPILER.getTask(null, new FileManagerWrapper(file), null, null, null, Collections.singletonList(file));
	}

	private static class MemoryJavaFileObject extends SimpleJavaFileObject {
		private final String className;
		private final CharSequence sourceCode;
		private final FastByteArrayOutputStream byteCode;

		public MemoryJavaFileObject(String className, CharSequence sourceCode) {
			super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
			this.className = className;
			this.sourceCode = sourceCode;
			this.byteCode = new FastByteArrayOutputStream();
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return sourceCode;
		}

		@Override
		public OutputStream openOutputStream() throws IOException {
			return byteCode;
		}

		public byte[] getByteCode() {
			return byteCode.toByteArray();
		}

		public String getClassName() {
			return className;
		}
	}

	private static class FileManagerWrapper extends ForwardingJavaFileManager<StandardJavaFileManager> {
		private final MemoryJavaFileObject file;

		public FileManagerWrapper(MemoryJavaFileObject file) {
			super(COMPILER.getStandardFileManager(null, null, null));
			this.file = file;
		}

		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
			if (!file.getClassName().equals(className)) {
				throw new IOException("Expected class with name " + file.getClassName() + ", but got " + className);
			}
			return file;
		}
	}





	// @formatter:off
	char UPPER_A          = 'A';
	char LOWER_A          = 'a';
	char UPPER_Z          = 'Z';
	char LOWER_Z          = 'z';
	char DOT              = '.';
	char AT               = '@';
	char LEFT_BRACE       = '{';
	char RIGHT_BRACE      = '}';
	char LEFT_BRACKET     = '(';
	char RIGHT_BRACKET    = ')';
	char DASH             = '-';
	char PERCENT          = '%';
	char PIPE             = '|';
	char PLUS             = '+';
	char QUESTION_MARK    = '?';
	char EXCLAMATION_MARK = '!';
	char EQUALS           = '=';
	char AMPERSAND        = '&';
	char ASTERISK         = '*';
	char STAR             = ASTERISK;
	char BACK_SLASH       = '\\';
	char COLON            = ':';
	char COMMA            = ',';
	char DOLLAR           = '$';
	char SLASH            = '/';
	char HASH             = '#';
	char HAT              = '^';
	char LEFT_CHEV        = '<';
	char NEWLINE          = '\n';
	char N                = 'n';
	char Y                = 'y';
	char QUOTE            = '\"';
	char RETURN           = '\r';
	char TAB              = '\t';
	char RIGHT_CHEV       = '>';
	char SEMICOLON        = ';';
	char SINGLE_QUOTE     = '\'';
	char BACKTICK         = '`';
	char SPACE            = ' ';
	char TILDA            = '~';
	char LEFT_SQ_BRACKET  = '[';
	char RIGHT_SQ_BRACKET = ']';
	char UNDERSCORE       = '_';
	char ONE              = '1';
	char ZERO             = '0';

}
