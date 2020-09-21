<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center">
    <form id="BundleUpdateForm" method="POST" enctype="multipart/form-data">
        <table>
            <tr>
                <th>启动Bundle:</th>
                <td><input type="checkbox" name="bundlestart" value="start" /></td>
            </tr>
            <tr>
                <th>刷新包:</th>
                <td><input type="checkbox" name="refreshPackages" value="refresh" /></td>
            </tr>
            <tr>
                <th>启动级别:</th>
                <td><input type="text" name="bundlestartlevel" value="80" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input id="bundlefile" type="file" size="45" name="bundlefile"
                    class="input"></td>
            </tr>

        </table>
    </form>
</div>
