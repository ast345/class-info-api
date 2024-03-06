package controllers

// @Injectを利用するためのもの。依存性注入に利用する。
import javax.inject._
// play frameworkのmvcを利用するためのもの。
import play.api.mvc._
// jsonを扱うためのパッケージ
import play.api.libs.json._
// modelsの取り込み
import models._
// 非同期処理に必要
import scala.concurrent.ExecutionContext


// ControllerComponentsはplay frameworkのコントローラー機能を提供するもの
// AbstractController(cc)は、Play Frameworkにおけるコントローラーの基本的な実装を提供するクラス
// studentServiceは、serviceレイヤーで定義
class StudentController @Inject()(cc: ControllerComponents, studentService: services.StudentService)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def list(facilitatorId: Long,  page: Option[Int], limit: Option[Int], order: Option[String], sort: Option[String], nameLike: Option[String], loginIdLike: Option[String]) = Action.async { implicit request =>
    studentService.findByFacilitator(facilitatorId, page, limit, order, sort, nameLike, loginIdLike).map { case (students, totalCount) =>
      val resultJson = Json.obj(
        "students" -> Json.toJson(students),
        "totalCount" -> totalCount
      )
      Ok(resultJson)
    }
  }
}