@Deprecated
项目已迁移至 https://github.com/v7lin/Android_Skin_3.x

# 前言
基于 Android Skin 1.0 修改而来，替换换肤解决方案，让换肤变得更加简单。
无须对项目作出太大修改，就可以完美实现换肤功能。
支持 Android 2.1(API>=7)，支持 AppCompat，支持普通的 new View（需手动添加换肤代码）。

# 特别申明
使用该代码用于生产时，请勿变更其类包名

# 原理
Android_Skin 是基于资源引用的唯一性而搭建的替换同名同类型资源的换肤支持。
通过重写LayoutInflater方法，在 xml 布局里面的视图初始化后，对其做相应的换肤功能的补充，得以根据资源引用的唯一性实现替换视图里面若干属性的。

# 缺陷
1.最致命的缺陷是对普通的 new View 的支持，稍显不足。
     不过对 new CompatView 的支持力，绝对是杆杆的。
     在替换法里面 CompatView 是用来替换 View 的，因为换肤的实现是在 CompatView 里面实现的。

2.对工程里的自定义视图，建议是改变继承，继承相对应的 CompatView，这样可以直接让自定义视图拥有换肤功能。

3.要实现对 Library 之外的视图属性换肤功能比较麻烦。
     首先要继承相应视图，实现 EnvCallback 和 XUICall 两个接口；其次重写相应换肤属性的函数，以实现换肤资源引用的替换和移除；最后可能还要写一个 EnvUIChanger。

# 注意事项
Library使用相关注意事项，请认真阅读《Library说明文档.doc》

# 效果
1.见 art/ptlive.mp4，不要用 Window 系统自带的 MediaPlayer 播放器观看。

2.下载体验麦潮1.98。

# BUG 修复
6.10 - 6.16：修正若干BUG（“麦潮”App的Android研发反馈）

6.18：兼容Android5.1（“麦潮”App的Android研发反馈）

9.2：ClassLoader 换肤模式有 BUG，换回 LayoutInflater 换肤模式（“睡啊”App的Android研发反馈）

9.10：修正 TextView 和 ListView 在动态换肤上出现的 BUG （“睡啊”App的Android研发反馈）

11.3：修正 5.x 上资源映射错误，并兼容 6.x 上取 Color 和 ColorStateList 资源（“麦潮”App的Android研发反馈）

# 重大版本迭代
7.3：Library 由 2.2 升级到 2.4。新增EnvViewMap。支持额外视图替换（AppCompat的支持不在话下，具体实现请阅读《Library说明文档.doc》）。

# SDK 应用
麦潮：http://www.varicom.im/

# License

   Copyright LinHenglong

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
