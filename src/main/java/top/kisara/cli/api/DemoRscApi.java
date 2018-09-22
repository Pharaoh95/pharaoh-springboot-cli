package top.kisara.cli.api;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class DemoRscApi {
    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }

    @GetMapping("/api/user/{id}")
    @RequiresPermissions("user-get")
    public HashMap user(@PathVariable String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", id);
        hashMap.put("password", "lxm8023kisara");
        return hashMap;
    }

    @GetMapping("/api/users")
    @RequiresPermissions("user-list")
    public List users() {
        List list = new ArrayList();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", "pharaoh");
        hashMap.put("password", "lxm8023kisara");
        list.add(hashMap);
        return list;
    }
}
