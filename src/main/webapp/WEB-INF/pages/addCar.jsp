<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle = "AddCar">
    <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/Cars/Create">
        <div class="col-md-6 mb-3">
            <label for="licensePlate">License Plate</label>
            <input type="text" class="form-control" id="license_plate" name="license_plate" placeholder="" value="" required>
            <div class="invalid-feedback">
                Valid License Plate is required.
            </div>
        </div>
        <div class="col-md-6 mb-3">
            <label for="parkingSpot">Parking Spot</label>
            <input type="text" class="form-control" id="parking_spot" name="parking_spot" placeholder="" value="" required>
            <div class="invalid-feedback">
                Valid Parking Spot is required.
            </div>
        </div>
        <div class="col-md-5 mb-3">
            <label for="owner">Owner</label>
            <select class="custom-select d-block w-100" id="owner_id" name ="owner_id" required>
                <option value="">Choose...</option>
                <c:forEach var = "user" items="${users}" varStatus="status">
                    <option value="${user.id}">${user.username}</option>
                </c:forEach>
            </select>
            <div class="invalid-feedback">
                Please select a valid Owner.
            </div>
        </div>
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" type="submit">Continue to checkout</button>
    </form>
    

</t:pageTemplate>

<script>
        (function () {
          'use strict'

          window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation')

            // Loop over them and prevent submission
            Array.prototype.filter.call(forms, function (form) {
              form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                  event.preventDefault()
                  event.stopPropagation()
                }

                form.classList.add('was-validated')
              }, false)
            })
          }, false)
        })()
    </script>