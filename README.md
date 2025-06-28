# ✏️📒 ふんわりとした瞬間を届ける、モグナビ
**MoguNavi**

---

**[仕様書]**  
[Mogunavi Application Manual PDF ダウンロード.pdf](https://github.com/user-attachments/files/20964900/Mogunavi.Application.Manual.PDF.pdf)


---

## 🧑‍💻 プロジェクト概要

| 項目 | 内容 |
| ---- | ---- |
| アプリ名 | モグナビ（MoguNavi） |
| 対応OS | Android 15 (API Level 36) / Minimum SDK: API Level 26 |
| 開発環境 | Android Studio Koala (2024.1.1) / Kotlin 2.0.21 |
| 開発言語 | Kotlin |
| 開発期間 | 2025.06.16 ～ 2025.06.29（14日間） |
| 使用API | Hot Pepper Gourmet API / Google Maps API |

---

## 📚 使用ライブラリ

| ライブラリ名 | バージョン | 用途 |
| ------------ | ---------- | ---- |
| AndroidX Activity Compose | 1.10.1 | Compose対応Activity |
| AndroidX Lifecycle / Core / Compose BOM | 2.9.1 / 1.16.0 | ライフサイクル管理 / KTX対応 |
| Accompanist Pager / Pager Indicators | 0.34.0 | スワイプ画面構成 |
| Accompanist Permissions | 0.34.0 | 位置情報パーミッション管理 |
| Coil Compose | 2.5.0 | 非同期画像読み込み |
| Google Maps API | 18.2.0 | 地図表示 |
| Google Play Services Location | 21.0.1 | 現在地取得 |
| Material3 | 1.2.1 | モダンUI構築 |
| Navigation Compose | 2.7.7 | Composeでの画面遷移 |
| Retrofit2 (＋GsonConverter) | 2.9.0 | API通信＆JSON変換 |

---

## 💫 機能一覧

1. **スプラッシュ画面**  
　アプリ起動時、ロゴを表示して自然な導線へ

![Splash-Photoroom](https://github.com/user-attachments/assets/7c6692d8-5c95-48df-89ea-387227e37d76)


3. **ホーム画面**  
　- Google Maps APIを使用して現在地取得  
　- Hot Pepper APIから周辺店舗をランダムに5件表示（スワイプ対応）
   

![home-Photoroom](https://github.com/user-attachments/assets/0ba4c639-280a-43d1-a53a-fcbb1f5e4864) ![bandicam 2025-06-29 06-57-59-065 (2)](https://github.com/user-attachments/assets/280bc126-c2fe-4341-b523-465f9c781e72)




4. **検索画面**  
　- 現在地からの検索半径設定機能  
　- ジャンルやキーワードでの検索フィルター  
　- 検索結果は1ページ最大10件（ページネーション機能付き）
   
![searchScreen-Photoroom](https://github.com/user-attachments/assets/a192650d-aa2a-40bb-a452-938ba870f145) ![Uploading bandicam 2025-06-29 07-02-45-069.gif…]()



5. **店舗詳細画面**  
　- 店舗の画像、名前、住所、営業時間を表示  
　- Googleマップ上に店舗の位置を表示  
　- 外部リンク共有機能あり
![DetailScreen-Photoroom](https://github.com/user-attachments/assets/c8e637dc-1833-454a-b52d-812b0aed0b36)

---


## 🙇‍♂️ コメント

本アプリはKotlinを初めて使用した開発体験から生まれました。  
実装中には多くの学びと課題がありましたが、それを通して  
「地図・位置情報・外部API連携」など実用的な技術を習得することができました。

---

