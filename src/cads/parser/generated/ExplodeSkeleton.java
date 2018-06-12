package cads.parser.generated;
import cads.org.Server.ServerController;
import cads.org.client.Order;
import cads.org.client.Service;
import cads.org.Middleware.Skeleton.ServiceSkeleton;;


public class ExplodeSkeleton  extends ServiceSkeleton{


  

	
	public  ExplodeSkeleton(int port, ServerController srv, int roboterID) {
        super(port, srv,roboterID);        
    }

	@Override
	public void useService(Order order) {
       super.getServerController().getRobot().getService(Service.EXPLODE).move(order);        
    }




}
 
