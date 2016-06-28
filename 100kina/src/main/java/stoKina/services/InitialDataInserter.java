package stoKina.services;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletException;

import stoKina.utils.DatabaseUtils;


@Singleton
@Startup
public class InitialDataInserter {
    
    @EJB
    private DatabaseUtils utils;
    
    public InitialDataInserter() {
    }
    
    @PostConstruct
    public void init() throws ServletException {
        utils.addTestDataToDB();
    }
}