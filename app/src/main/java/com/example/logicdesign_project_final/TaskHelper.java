package com.example.logicdesign_project_final;

import android.view.View;
public class TaskHelper {
    String id, check, type, mcp, description, route, userId;
    public TaskHelper() {
    }
    public TaskHelper(String id, String check, String type, String mcp, String description, String route, String userId) {
        this.id = id;
        this.check = check;
        this.type = type;
        this.mcp = mcp;
        this.description = description;
        this.route = route;
        this.userId = userId;
    }
}
