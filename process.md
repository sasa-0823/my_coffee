## データベース作成
- データベースに接続(application.propertiesに設定を記載)
- templates内のsqlファイルにテーブル作成のsqlを記載
- 必要なデータがあればinsertも記載してデータを入れる

## データベースのCRUDの準備
- データベースの各テーブルをオブジェクトとして扱う為にEntityアノテーションをつけたクラスで各テーブルのエンティティを宣言する(クラスに@Dataをつけてgetter ,setterは自動生成)
- repositoryインターフェイスも作成し、JpaRepositoryクラスを継承してCRUDを行えるようにする(のちにserviceクラスに継承)

## ログインフォーム作成→ログインまで
- @Configuration,@EnableWebSecurity,@EnableMethodSecurityアノテーションを付けたクラスにてセキュリティの設定を行う
- UserDetailesインターフェイスを実装(implements)したクラスを作成しユーザー情報を定義しているエンティティクラスを注入、またゲッターで取得した値を返すようにメソッドをオーバーライドする。
- セキュリティを設定しているファイル内にて、ログインフォームのURLを指定、ログインできるルートパス、ログイン時、ログイン後のルートパスを指定する。(この際にログイン後のhtmlファイルも記載すれば確認がしやすい。)
- メールアドレスからユーザー名を検索できるようにユーザーエンティティを操作するリポジトリにメソッドを各(例：public User findByEmail(String email);)
- ログインするユーザー情報のビジネスロックを担当するクラスを作成する（UserDetailServiceインターフェイスを実装）、またユーザー名を取得する為UserRepositoryを継承する
- 