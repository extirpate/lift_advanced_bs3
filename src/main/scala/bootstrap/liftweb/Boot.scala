package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import mapper._

import code.model._
import net.liftmodules.FoBo

import scala.language.postfixOps

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(util.DefaultConnectionIdentifier, vendor)
    }

    // Use Lift's Mapper ORM to populate the database
    // you don't need to use Mapper to use Lift... use
    // any ORM you want
    Schemifier.schemify(true, Schemifier.infoF _, User)

    // where to search snippet
    LiftRules.addToPackages("code")


    def sitemapMutators = User.sitemapMutator
    //The SiteMap is built in the Site object bellow 
    LiftRules.setSiteMapFunc(() => sitemapMutators(Site.sitemap))

    //Init the FoBo - Front-End Toolkit module, 
    //see http://liftweb.net/lift_modules for more info
    FoBo.InitParam.JQuery=FoBo.JQuery191
    //FoBo.InitParam.ToolKit=FoBo.PrettifyJun2011
    FoBo.InitParam.ToolKit=FoBo.Bootstrap320
    //FoBo.InitParam.ToolKit=FoBo.FontAwesome321
    FoBo.InitParam.ToolKit=FoBo.AngularJS1219      //access to core files
    FoBo.InitParam.ToolKit=FoBo.AngularJS1219i18n   //access to angular i18n files
    FoBo.InitParam.ToolKit=FoBo.AJSUIBootstrap070 //Angular UI Bootstrap
    FoBo.InitParam.ToolKit=FoBo.AJSNGGrid207      //Angular NG-Grid
    FoBo.init()

      //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))    
      
    LiftRules.noticesAutoFadeOut.default.set( (notices: NoticeType.Value) => {
        notices match {
          case NoticeType.Notice => Full((8 seconds, 4 seconds))
          case _ => Empty
        }
     }
    ) 
    
    // Make a transaction span the whole HTTP request
    S.addAround(DB.buildLoanWrapper)
  }
  
  object Site {
    import scala.xml._
    val divider1   = Menu("divider1") / "divider1"
    val ddLabel1   = Menu.i("UserDDLabel") / "ddlabel1"
    val home       = Menu.i("Home") / "index" 
    val userMenu   = User.AddUserMenusHere
    val static     = Menu(Loc("Static", Link(List("static"), true, "/static/index"), S.loc("StaticContent" , scala.xml.Text("Static Content")),LocGroup("lg2","topRight")))
   val twbs  = Menu(Loc("twbs", 
        ExtLink("http://getbootstrap.com/"), 
        S.loc("Bootstrap3", Text("Bootstrap3")), 
        LocGroup("lg2"),
        FoBo.TBLocInfo.LinkTargetBlank ))     
    
    
    def sitemap = SiteMap(
        home          >> LocGroup("lg1"),
        static,
        twbs,
        ddLabel1      >> LocGroup("topRight") >> PlaceHolder submenus (
            divider1  >> FoBo.TBLocInfo.Divider >> userMenu
            )
         )
  }

/*  net.liftmodules.ng.AngularJS.init(
    // Modules to be included by default.  angular.js is assumed.
    "animate", "cookies", "loader", "resource", "route", "sanitize", "touch"
  )*/

/*  net.liftmodules.ng.Angular.init(
    // Set to true if you plan to use futures. False otherwise to avoid an unneeded comet
    futures = true,

    // Set to the CSS selector for finding your apps in the page.
    appSelector = "[ng-app]",

    // Set to true to include a script tag with the src set to the path for liftproxy.js.
    // Set to false if you want to handle that yourself by referring to the path in
    // net_liftmodules_ng.
    includeJsScript = true
  )*/

  //val context:ExecutionContext = // Create context
    //net.liftmodules.ng.AngularExecutionContext(context) // Tell lift-ng to use it

}
