# 特别申明
使用该代码用于生产时，请勿变更其类包名

# 原理
Android_Skin 是基于资源引用的唯一性搭建的换肤支持。通过重写LayoutInflater或ClassLoader两种方法，替换 xml 布局里面的相应视图，得以根据资源引用的唯一性实现替换视图里面若干属性的。支持 Android 2.1(API>=7) 以上应用。

# 缺陷
1.最致命的缺陷是不支持普通原生的new View，当然 new CompatView 是支持的。在替换法里面 CompatView 是用来替换 View 的，因为换肤的实现是在 CompatView 里面实现的。

2.要实现对 Library 之外的视图换肤功能比较麻烦。要继承相应视图，实现 EnvCallback 和 XUICall 两个接口；还要重写相应换肤属性的函数，以实现换肤资源引用的替换和移除；最后可能还要写一个 EnvUIChanger。

# 改进
Android_Skin 3.0 的改进方案已经成熟，当然也暴露了一个不大不小的缺陷。

# 注意事项
Library使用相关注意事项，请认真阅读《换肤换字体注意事项.doc》

# 效果
1.见 art/ptlive.mp4，不要用 Window 系统自带的 MediaPlayer 播放器观看。

2.下载体验麦潮1.98。

# BUG 修复
6.10 - 6.16：修正若干BUG（“麦潮”App的Android研发负责人反馈）

6.18：兼容Android5.1（“麦潮”App的Android研发负责人反馈）

# 重大版本迭代
7.3：Library 由 2.2 升级到 2.4。替换换肤方案（将LayoutInflater方案，替换为ClassLoader方案），新增EnvViewMap。支持额外视图替换（appcompat的支持不在话下，具体实现请阅读《换肤换字体注意事项.doc》）。

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
