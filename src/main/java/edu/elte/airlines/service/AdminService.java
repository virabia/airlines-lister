package edu.elte.airlines.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import edu.elte.airlines.dto.DtoInterface;
import edu.elte.airlines.response.CustomResponse;
import edu.elte.airlines.response.CustomResponseFactory;
import edu.elte.airlines.service.interfaces.ICrudService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdminService {

    private final ICrudService crudService;
    private final CustomResponseFactory customResponseFactory;
    private static Logger logger = LoggerFactory.getLogger(AdminService.class);
    public AdminService(ICrudService<?, DtoInterface, String> crudService, CustomResponseFactory customResponseFactory) {
        this.crudService = crudService;
        this.customResponseFactory = customResponseFactory;
    }

    public CustomResponse list() {
        CustomResponse response;
        try {
            response = customResponseFactory.successfullResponse(crudService.list());
        } catch (Exception ex) {
            logger.error(ex);
            response = customResponseFactory.errorResponse(ex);
        }
        return response;
    }
    public CustomResponse create(DtoInterface dtoObject) {
        CustomResponse response;
        try {
            crudService.create(dtoObject);
            response = customResponseFactory.successfullResponse();
        } catch (Exception ex) {
            logger.error(ex);
            response = customResponseFactory.errorResponse(ex);
        }
        return response;
    }
    public CustomResponse update(DtoInterface dtoObject) {
        CustomResponse response;
        try {
            crudService.update(dtoObject);
            response = customResponseFactory.successfullResponse();
        } catch (Exception ex) {
            logger.error(ex);
            response = customResponseFactory.errorResponse(ex);
        }
        return response;
    }
    public CustomResponse delete(DtoInterface dtoObject) {
        CustomResponse response;
        try {
            DtoInterface temp = (DtoInterface) crudService.findById(dtoObject.getId());
            crudService.delete(temp);
            response = customResponseFactory.successfullResponse();
        } catch (Exception ex) {
            logger.error(ex);
            response = customResponseFactory.errorResponse(ex);
        }
        return response;
    }
    public CustomResponse findById(Integer id) {
        CustomResponse response;
        try {
            DtoInterface dtoObject = (DtoInterface) crudService.findById(id);
            response = customResponseFactory.successfullResponse(dtoObject);
        } catch (Exception ex) {
            logger.error(ex);
            response = customResponseFactory.errorResponse(ex);
        }
        return response;
    }
    public CustomResponse exists(Integer id) {
        CustomResponse response;
        try {
            Boolean result = crudService.exists(id);
            response = customResponseFactory.successfullResponse(result);
        } catch (Exception ex) {
            response = customResponseFactory.errorResponse(ex);
        }
        return response;
    }
}