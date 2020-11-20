/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.park.parkinglot.ejb;

import com.park.parkinglot.common.CarDetails;
import com.park.parkinglot.entity.Car;
import com.park.parkinglot.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dani
 */
@Stateless
public class CarBean {

    private static final Logger LOG = Logger.getLogger(CarBean.class.getName());

    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public CarDetails findById(Integer carId) {
        Car car = em.find(Car.class, carId);
        return new CarDetails(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getUser().getUsername());
    }
    
    public void deleteCarsByIds(Collection<Integer> carIds) {
        LOG.info("deleteCarsByIds");
        for(Integer id : carIds){
            Car car = em.find(Car.class,id);
            em.remove(car);
        }
    }
    
    public List<CarDetails> getAllCars() {
        LOG.info("getAllCars");
        try {
            List<Car> cars = (List<Car>) em.createQuery("SELECT c FROM Car c").getResultList();
            /* Another method 
            TypedQuery<Car> typedQuery = em.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            */
            return copyCarsToDetails(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
    
    public List<CarDetails> copyCarsToDetails(List<Car> cars) {
        List<CarDetails> detailsList = new ArrayList<>();
        for (Car car : cars) {
            CarDetails  carDetails = new CarDetails(car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getUser().getUsername());
            detailsList.add(carDetails);      
        }
        return detailsList;
    }
    
    public void createCar(String licensePlate, String parkingSpot, Integer userId) {
        LOG.info("createCar");
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);
        
        User user = em.find(User.class, userId);
        user.getCars().add(car);
        car.setUser(user);
        
        em.persist(car);
    }

    public void updateCar(int carId, String licensePlate, String parkingLot, int ownerId) {
        LOG.info("updateCar");
        Car car = em.find(Car.class,carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingLot);
                
        User oldUser = car.getUser();
        oldUser.getCars().add(car);
        
        User user = em.find(User.class, ownerId);
        user.getCars().add(car);
        car.setUser(oldUser);
    }
}
