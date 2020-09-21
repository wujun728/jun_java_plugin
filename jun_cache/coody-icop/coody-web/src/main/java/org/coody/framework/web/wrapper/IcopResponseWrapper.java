package org.coody.framework.web.wrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.web.container.HttpContainer;

@AutoBuild
public class IcopResponseWrapper implements ServletResponse, HttpServletResponse {

	@Override
	public void addCookie(Cookie arg0) {
		HttpContainer.getResponse().addCookie(arg0);

	}

	@Override
	public void addDateHeader(String arg0, long arg1) {
		HttpContainer.getResponse().addDateHeader(arg0, arg1);

	}

	@Override
	public void addHeader(String arg0, String arg1) {
		HttpContainer.getResponse().addHeader(arg0, arg1);

	}

	@Override
	public void addIntHeader(String arg0, int arg1) {
		HttpContainer.getResponse().addIntHeader(arg0, arg1);

	}

	@Override
	public boolean containsHeader(String arg0) {
		return HttpContainer.getResponse().containsHeader(arg0);
	}

	@Override
	public String encodeRedirectURL(String arg0) {
		return HttpContainer.getResponse().encodeRedirectURL(arg0);
	}

	@Override
	@Deprecated
	public String encodeRedirectUrl(String arg0) {
		return HttpContainer.getResponse().encodeRedirectURL(arg0);
	}

	@Override
	public String encodeURL(String arg0) {
		return HttpContainer.getResponse().encodeURL(arg0);
	}

	@Override
	@Deprecated
	public String encodeUrl(String arg0) {
		return HttpContainer.getResponse().encodeURL(arg0);
	}

	@Override
	public void sendError(int arg0) throws IOException {
		HttpContainer.getResponse().sendError(arg0);
	}

	@Override
	public void sendError(int arg0, String arg1) throws IOException {
		HttpContainer.getResponse().sendError(arg0, arg1);
	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		HttpContainer.getResponse().sendRedirect(arg0);
		;
	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		HttpContainer.getResponse().setDateHeader(arg0, arg1);
	}

	@Override
	public void setHeader(String arg0, String arg1) {
		HttpContainer.getResponse().setHeader(arg0, arg1);

	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		HttpContainer.getResponse().setIntHeader(arg0, arg1);

	}

	@Override
	public void setStatus(int arg0) {
		HttpContainer.getResponse().setStatus(arg0);

	}

	@Override
	public void setStatus(int arg0, String arg1) {
		HttpContainer.getResponse().setStatus(arg0);

	}

	@Override
	public void flushBuffer() throws IOException {
		HttpContainer.getResponse().flushBuffer();

	}

	@Override
	public int getBufferSize() {
		return HttpContainer.getResponse().getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		return HttpContainer.getResponse().getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return HttpContainer.getResponse().getContentType();
	}

	@Override
	public Locale getLocale() {
		return HttpContainer.getResponse().getLocale();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return HttpContainer.getResponse().getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return HttpContainer.getResponse().getWriter();
	}

	@Override
	public boolean isCommitted() {
		return HttpContainer.getResponse().isCommitted();
	}

	@Override
	public void reset() {
		HttpContainer.getResponse().reset();

	}

	@Override
	public void resetBuffer() {
		HttpContainer.getResponse().resetBuffer();

	}

	@Override
	public void setBufferSize(int arg0) {
		HttpContainer.getResponse().setBufferSize(arg0);

	}

	@Override
	public void setCharacterEncoding(String arg0) {
		HttpContainer.getResponse().setCharacterEncoding(arg0);

	}

	@Override
	public void setContentLength(int arg0) {
		HttpContainer.getResponse().setContentLength(arg0);

	}

	@Override
	public void setContentType(String arg0) {
		HttpContainer.getResponse().setContentType(arg0);

	}

	@Override
	public void setLocale(Locale arg0) {
		HttpContainer.getResponse().setLocale(arg0);

	}

}
