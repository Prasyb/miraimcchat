# MiraiMcChat
![Github](https://img.shields.io/badge/Author-Prasyb-blue)  

基于 [mirai-console](https://github.com/mamoe/mirai-console) 的插件，提供QQ群与Minecraft的聊天同步功能

本插件为服务端，需配合Mod [MiraiMcChat-client]() 使用

## 使用方法

### 1.构建 mirai-console 

- **[Mirai 用户手册](https://github.com/mamoe/mirai/blob/dev/docs/UserManual.md)**

### 2.添加插件

将插件jar文件放入 mirai-console 根目录下 `/plugins/` 文件夹下并启动 mirai-console

若需要在QQ聊天环境下执行插件相关指令，可选择添加 [chat-command](https://github.com/project-mirai/chat-command) 插件

- **关于QQ聊天环境下执行指令的权限问题**
  
见 [Mirai Console 内置指令](https://github.com/mamoe/mirai-console/blob/master/docs/BuiltInCommands.md#mirai-console---builtin-commands) 的 PermissionCommand 条目

本插件目前拥有 2 个权限节点：

- 普通用户权限节点 `net.prasyb.miraimcchat:command.mcchat`
- 管理员权限节点 `net.prasyb.miraimcchat:command.mcchatop`
### 3.初始化设置
- 使用 `/mcchatop setport <port>` 设定本地端口，并按需开放该端口或使用端口映射

- 使用 `/mcchatop bindgroup <groupid>` 绑定QQ群号

- 使用 `/mcchatop enable` 开启消息同步功能

- 使用 `/mcchatop register <clientname>` 注册客户端，并保存 key

- 使用 key 配置 MiraiMcChat-client

### 4.普通用户绑定账号
- 使用 `/mcchat bind <minecraftname>` 绑定QQ号与 minecraft 用户名