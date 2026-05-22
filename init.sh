#!/bin/bash

echo "=========================================="
echo "   jun_java_plugin 环境检查和初始化"
echo "=========================================="
echo ""

# 颜色定义
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 检查函数
check_command() {
    if command -v $1 &> /dev/null; then
        echo -e "${GREEN}✓${NC} $1 已安装"
        if [ "$1" == "java" ]; then
            java -version 2>&1 | head -n 1
        elif [ "$1" == "mvn" ]; then
            mvn -version 2>&1 | head -n 1
        fi
        return 0
    else
        echo -e "${RED}✗${NC} $1 未安装"
        return 1
    fi
}

# 1. 检查前置条件
echo "1. 检查开发环境..."
echo "----------------------------"

check_command java
JAVA_OK=$?

check_command mvn
MVN_OK=$?

check_command git
GIT_OK=$?

echo ""

if [ $JAVA_OK -ne 0 ] || [ $MVN_OK -ne 0 ]; then
    echo -e "${RED}错误: 缺少必要的开发工具${NC}"
    echo "请安装以下工具:"
    [ $JAVA_OK -ne 0 ] && echo "  - JDK 1.8+"
    [ $MVN_OK -ne 0 ] && echo "  - Maven 3.5+"
    exit 1
fi

# 2. 检查项目结构
echo "2. 检查项目结构..."
echo "----------------------------"

MODULES=("jun_java_plugins" "jun_springboot_plugin" "jun_springboot_starter" "jun_springcloud_plugin" "java_project_template")

for module in "${MODULES[@]}"; do
    if [ -d "$module" ]; then
        echo -e "${GREEN}✓${NC} $module 目录存在"
    else
        echo -e "${YELLOW}⚠${NC} $module 目录不存在"
    fi
done

echo ""

# 3. 统计模块数量
echo "3. 统计项目模块..."
echo "----------------------------"

for module in "${MODULES[@]}"; do
    if [ -d "$module" ]; then
        count=$(find "$module" -maxdepth 1 -type d | grep -v "^$module$" | wc -l)
        echo "$module: $count 个子模块"
    fi
done

echo ""

# 4. 检查 Maven 依赖
echo "4. 检查 Maven 配置..."
echo "----------------------------"

if [ -f "pom.xml" ]; then
    echo -e "${GREEN}✓${NC} 找到根 pom.xml"
    echo "项目版本: $(grep -m 1 '<version>' pom.xml | sed 's/.*<version>\(.*\)<\/version>.*/\1/')"
else
    echo -e "${RED}✗${NC} 未找到根 pom.xml"
    exit 1
fi

echo ""

# 5. 清理和编译检查(可选)
echo "5. 编译检查选项..."
echo "----------------------------"
echo "是否执行 Maven 编译检查? (y/n)"
echo -e "${YELLOW}注意: 完整编译可能需要 5-10 分钟${NC}"
read -p "请选择: " compile_choice

if [ "$compile_choice" == "y" ] || [ "$compile_choice" == "Y" ]; then
    echo ""
    echo "开始编译..."
    export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"

    # 只编译不测试
    mvn clean compile -DskipTests -T 4

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ 编译成功${NC}"
    else
        echo -e "${RED}✗ 编译失败，请查看错误信息${NC}"
        echo "提示: 某些模块可能有依赖冲突，这在演示仓库中是正常的"
    fi
else
    echo "跳过编译检查"
fi

echo ""

# 6. 冒烟测试
echo "6. 冒烟测试..."
echo "----------------------------"

# 检查是否有编译产物
if [ -d "target" ]; then
    echo -e "${GREEN}✓${NC} target 目录存在"
else
    echo -e "${YELLOW}⚠${NC} target 目录不存在，未进行编译"
fi

# 检查关键文件
if [ -f "task_list.json" ]; then
    echo -e "${GREEN}✓${NC} 任务规划文件存在"
    task_count=$(grep -o '"id"' task_list.json | wc -l)
    echo "  - 共 $task_count 个任务"
fi

if [ -f "CLAUDE.md" ]; then
    echo -e "${GREEN}✓${NC} CLAUDE.md 文档存在"
fi

echo ""

# 7. 总结
echo "=========================================="
echo "   环境检查完成"
echo "=========================================="
echo ""
echo "下一步操作:"
echo "1. 阅读 README.md 了解项目结构"
echo "2. 查看 task_list.json 了解任务规划"
echo "3. 参考 CLAUDE.md 了解开发规范"
echo "4. 使用 /crud 技能生成代码"
echo ""
echo "常用命令:"
echo "  mvn clean compile         # 编译项目"
echo "  mvn clean package         # 打包项目"
echo "  mvn clean install         # 安装到本地仓库"
echo ""
echo -e "${GREEN}初始化完成!${NC}"
