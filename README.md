# ItemSigner

一個簡單、輕量的 Paper 插件，讓玩家可以在物品上留下自己的署名。

## ✨ 功能特色

- 使用 `/sign` 為手上的物品署名
- 防止重複署名
- 保留原有 Lore 資訊
- 使用 `/unsign` 移除署名
- 管理員可移除其他玩家的署名
- 輕量化設計，不影響伺服器效能

---

## 📋 指令

| 指令 | 說明 |
|------|------|
| `/sign` | 為手中的物品添加署名 |
| `/unsign` | 移除物品上的署名 |

---

## 🔑 權限

| 權限節點 | 說明 | 預設 |
|----------|------|------|
| `itemsigner.sign` | 使用 `/sign` | `true` |
| `itemsigner.unsign` | 使用 `/unsign` | `true` |
| `itemsigner.unsign.other` | 移除其他玩家的署名 | `op` |

---

## 🖼 使用範例

### 署名前

```text
鑽石劍
```

### 執行指令

```text
/ sign
```

### 署名後

```text
鑽石劍

✦ 來自：www ✦
```

---

## ⚙️ 支援版本

- Minecraft 1.21.1+
- Paper
- Java 21

---

## 🔨 自行編譯

### Windows

```bat
gradlew.bat build
```

### Linux

```bash
./gradlew build
```

編譯完成後的檔案位於：

```text
build/libs/
```

---

## 📦 安裝方式

1. 下載最新版本的插件。
2. 將 Jar 檔放入伺服器的 `plugins` 資料夾。
3. 啟動或重新啟動伺服器。
4. 安裝完成。

---

## 📁 專案結構

```text
src/
├─ main/
│  ├─ java/
│  └─ resources/
│      └─ plugin.yml
├─ build.gradle
└─ settings.gradle
```

---

## ❤️ 為什麼使用 ItemSigner？

當玩家獲得稀有物品、活動紀念品、管理員贈送裝備時，可以透過署名功能保留來源資訊，增加收藏價值與紀念意義。

---

## 📜 License

本專案採用 MIT License 開源授權。

歡迎 Fork、修改及二次開發。
