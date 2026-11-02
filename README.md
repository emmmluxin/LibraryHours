# 图书馆

一个只看一件事的安卓应用：**图书馆现在开没开门。**

从 DayFlow 的「图书」页单独抠出来，去掉了一切与「判断营业状态」无关的东西——没有保活、没有服务、没有权限申请、没有后台任务，打开就是答案。

## 功能

- **当前状态**：开馆中 / 未开馆 / 已闭馆 / 今日闭馆
- **倒计时**：距闭馆或距开馆还剩多久，以及具体时刻
- **本周**：七格一目了然，周一闭馆标红
- **分季时段**：夏令与冬令两套时间，当季那张描边打勾
- **样式页**：两套配色（海蓝 / 胭脂）、两种卡片形态（立体 / 描边），选择本地保存

## 开放时间规则

| 项 | 规则 |
| --- | --- |
| 闭馆日 | 每周一 |
| 开放日 | 周二至周日 |
| 夏令 5–9 月 | 08:30 – 20:30 |
| 冬令 10–4 月 | 08:30 – 19:30 |

规则集中在 `app/src/main/java/com/anyuan/library/data/LibraryHours.kt` 的 `LibraryRepository`，改学校/单位的实际时段只用改这一个文件。

## 构建

```bash
git clone <repo>
cd LibraryHours
./gradlew assembleRelease
```

产物：`app/build/outputs/apk/release/app-release.apk`

要求 JDK 17 以上、Android SDK 已配置（`local.properties` 里的 `sdk.dir` 或环境变量 `ANDROID_HOME`）。`local.properties` 不进仓库。

## 技术栈

Kotlin + Jetpack Compose + Material 3，`minSdk 26` / `targetSdk 36`，无第三方依赖。

主题跟随系统深浅，没有独立开关。
