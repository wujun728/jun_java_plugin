package io.github.wujun728.rest;

import io.github.wujun728.rest.util.RestUtil;
import io.github.wujun728.rest.util.TreeBuildUtil;
import io.github.wujun728.sql.entity.Result;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * REST 动态 API 模块单元测试
 */
public class RestApiTest {

    @Test
    public void testTreeBuildUtilListToTree() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> root = new HashMap<>();
        root.put("id", 1L);
        root.put("pid", 0L);
        root.put("name", "Root");
        list.add(root);

        Map<String, Object> child1 = new HashMap<>();
        child1.put("id", 2L);
        child1.put("pid", 1L);
        child1.put("name", "Child1");
        list.add(child1);

        Map<String, Object> child2 = new HashMap<>();
        child2.put("id", 3L);
        child2.put("pid", 1L);
        child2.put("name", "Child2");
        list.add(child2);

        Map<String, Object> grandChild = new HashMap<>();
        grandChild.put("id", 4L);
        grandChild.put("pid", 2L);
        grandChild.put("name", "GrandChild");
        list.add(grandChild);

        List<Map<String, Object>> tree = TreeBuildUtil.listToTree(list, "0", "id", "pid");
        assertNotNull("Tree should not be null", tree);
        assertEquals("Should have 1 root", 1, tree.size());

        Map<String, Object> rootNode = tree.get(0);
        assertEquals("Root", rootNode.get("name"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> children = (List<Map<String, Object>>) rootNode.get("children");
        assertNotNull("Root should have children", children);
        assertEquals("Root should have 2 children", 2, children.size());
    }

    @Test
    public void testTreeBuildUtilEmptyList() {
        List<Map<String, Object>> emptyList = new ArrayList<>();
        List<Map<String, Object>> tree = TreeBuildUtil.listToTree(emptyList, "0", "id", "pid");
        assertNotNull(tree);
        assertTrue("Empty list should produce empty tree", tree.isEmpty());
    }

    @Test
    public void testTreeBuildUtilCustomChildrenField() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> parent = new HashMap<>();
        parent.put("id", "A");
        parent.put("parentId", "0");
        parent.put("title", "Parent");
        list.add(parent);

        Map<String, Object> child = new HashMap<>();
        child.put("id", "B");
        child.put("parentId", "A");
        child.put("title", "Child");
        list.add(child);

        List<Map<String, Object>> tree = TreeBuildUtil.listToTree(list, "0", "id", "parentId", "items");
        assertEquals(1, tree.size());

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items = (List<Map<String, Object>>) tree.get(0).get("items");
        assertNotNull("Should use custom children field name", items);
        assertEquals(1, items.size());
    }

    @Test
    public void testTreeBuildUtilOrphanNodes() {
        // Nodes whose parent doesn't exist should be treated as root
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> orphan = new HashMap<>();
        orphan.put("id", 10L);
        orphan.put("pid", 999L); // parent doesn't exist
        orphan.put("name", "Orphan");
        list.add(orphan);

        List<Map<String, Object>> tree = TreeBuildUtil.listToTree(list, "0", "id", "pid");
        assertEquals("Orphan should be root", 1, tree.size());
    }

    @Test
    public void testRestUtilGetNullPropertyNames() {
        TestBean bean = new TestBean();
        bean.setName("test");
        // age and email are null
        String[] nullProps = RestUtil.getNullPropertyNames(bean);
        assertNotNull(nullProps);
        assertTrue("Should find null properties", nullProps.length > 0);

        // Verify 'name' is not in null properties
        Set<String> nullSet = new HashSet<>(Arrays.asList(nullProps));
        assertFalse("name should not be null", nullSet.contains("name"));
    }

    @Test
    public void testResultSuccess() {
        Result result = Result.success("test data");
        assertEquals(0, result.get("code"));
        assertEquals(0, result.get("status")); // amis compatibility
        assertEquals("success", result.get("msg"));
        assertEquals("test data", result.get("data"));
    }

    @Test
    public void testResultError() {
        Result result = Result.error("something failed");
        assertEquals(500, result.get("code"));
        assertEquals(500, result.get("status")); // amis compatibility
        assertEquals("something failed", result.get("msg"));
    }

    @Test
    public void testResultFail() {
        Result result = Result.fail("validation error");
        assertEquals(500, result.get("code"));
        assertEquals(500, result.get("status"));
        assertEquals("validation error", result.get("msg"));
    }

    @Test
    public void testResultChainPut() {
        Result result = Result.success("data")
                .put("count", 100)
                .put("pageSize", 10);
        assertEquals("data", result.get("data"));
        assertEquals(100, result.get("count"));
        assertEquals(10, result.get("pageSize"));
    }

    @Test
    public void testResultAmisCompatibility() {
        // amis expects {status: 0, msg: "", data: ...}
        Result success = Result.success("ok data");
        assertEquals("status should be 0 for success", 0, success.get("status"));
        assertEquals("code should be 0 for success", 0, success.get("code"));

        Result error = Result.error(400, "bad request");
        assertEquals("status should be 400 for error", 400, error.get("status"));
        assertEquals("code should be 400 for error", 400, error.get("code"));
    }

    // Simple test bean for getNullPropertyNames test
    public static class TestBean {
        private String name;
        private Integer age;
        private String email;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
