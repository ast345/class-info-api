package controllers

import javax.inject._
import play.api.mvc._
import utils.DatabaseSeeder


class DatabaseController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def seedDatabase = Action {
    DatabaseSeeder.seed()
    Ok("データベースにシードデータが挿入されました。")
  }
}
