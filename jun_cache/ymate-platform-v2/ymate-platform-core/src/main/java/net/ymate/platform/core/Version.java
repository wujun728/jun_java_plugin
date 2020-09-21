/*
 * Copyright 2007-2017 the original author or authors.
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
package net.ymate.platform.core;

/**
 * 版本信息描述类
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/12 下午11:31
 * @version 1.0
 */
public class Version {

    public enum VersionType {
        Alphal, // α（alphal） 内部测试版
        Beta,   // β（beta）外部测试版
        GA,     // γ（gamma）版
        Trial,  // trial（试用版）
        Demo,   // demo 演示版
        Release // release 最终释放版
    }

    private int majorVersion;

    private int minorVersion;

    private int revisionNumber;

    private String buildNumber;

    private VersionType versionType;

    public Version(int majorVersion, int minorVersion, int revisionNumber) {
        this(majorVersion, minorVersion, revisionNumber, null, null);
    }

    public Version(int majorVersion, int minorVersion, int revisionNumber, VersionType versionType) {
        this(majorVersion, minorVersion, revisionNumber, null, versionType);
    }

    public Version(int majorVersion, int minorVersion, int revisionNumber, String buildNumber, VersionType versionType) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.revisionNumber = revisionNumber;
        if (buildNumber == null) {
            buildNumber = Version.class.getPackage().getImplementationVersion();
        }
        this.buildNumber = buildNumber;
        this.versionType = versionType;
    }

    /**
     * @return 返回主版本号
     */
    public int getMajorVersion() {
        return majorVersion;
    }

    /**
     * @return 返回子版本号
     */
    public int getMinorVersion() {
        return minorVersion;
    }

    /**
     * @return 返回修正版本号
     */
    public int getRevisionNumber() {
        return revisionNumber;
    }

    /**
     * @return 返回编译版本号
     */
    public String getBuildNumber() {
        return buildNumber;
    }

    public VersionType getVersionType() {
        return versionType;
    }

    @Override
    public String toString() {
        StringBuilder _versionSB = new StringBuilder()
                .append(majorVersion).append(".")
                .append(minorVersion).append(".")
                .append(revisionNumber);
        if (versionType != null) {
            _versionSB.append("-").append(versionType);
        }
        if (buildNumber != null) {
            _versionSB.append(" build-").append(buildNumber);
        }
        return _versionSB.toString();
    }
}
