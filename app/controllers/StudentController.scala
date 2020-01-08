package controllers


import java.util.Date

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import org.webjars.play.WebJarsUtil
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import protocols.StudentProtocol.{AddStudent, Student}
import views.html.student._

import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext

@Singleton
class StudentController @Inject()(val controllerComponents: ControllerComponents,
                                  @Named("student-manager") val studentManager: ActorRef,
                                  implicit val webJarsUtil: WebJarsUtil
                                 )
                                 (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  implicit val defaultTimeout: Timeout = Timeout(60.seconds)

  def index: Action[AnyContent] = Action {
    studentPost
    Ok(views.html.student(""))
  }

  def studentPost = {
    logger.warn(s"Keldi.........")
    (studentManager ? AddStudent(Student(None, "Maftunbek", "Raxmatov", new Date, 123546))).mapTo[Int].map { pr =>
      Ok(Json.toJson(s"ajji: $pr"))
    }
  }

}
