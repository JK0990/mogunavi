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

![searchScreen](https://github.com/user-attachments/assets/88dca023-176e-4d7b-bfad-2204e22709b4)

3. **ホーム画面**  
　- Google Maps APIを使用して現在地取得  
　- Hot Pepper APIから周辺店舗をランダムに5件表示（スワイプ対応）
   
![bandicam 2025-06-29 07-02-45-069](https://github.com/user-attachments/assets/04106a9f-fd7d-42a9-928f-13e2783df74f) ![bandicam 2025-06-29 06-57-59-065](https://github.com/user-attachments/assets/415990a2-232a-4338-9186-c8cb9fd2f40b)



4. **検索画面**  
　- 現在地からの検索半径設定機能  
　- ジャンルやキーワードでの検索フィルター  
　- 検索結果は1ページ最大10件（ページネーション機能付き）
   
![bandicam 2025-06-29 07-02-45-069](https://github.com/user-attachments/assets/48511157-6445-48d3-9761-f540db4f4910)

5. **店舗詳細画面**  
　- 店舗の画像、名前、住所、営業時間を表示  
　- Googleマップ上に店舗の位置を表示  
　- 外部リンク共有機能あり

---


## 🙇‍♂️ コメント

本アプリはKotlinを初めて使用した開発体験から生まれました。  
実装中には多くの学びと課題がありましたが、それを通して  
「地図・位置情報・外部API連携」など実用的な技術を習得することができました。

---

