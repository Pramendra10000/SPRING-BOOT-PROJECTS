<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register Page</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
  <link href="${pageContext.request.contextPath}/CSS/Register.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>
<body>

<section class="vh-100">
  <div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
      <!-- Left column (Image) -->
      <div class="col-md-9 col-lg-6 col-xl-5">
        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp" class="img-fluid" alt="Sample image">
      </div>

      <!-- Right column (Form) -->
      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
        <form action="${pageContext.request.contextPath}/register" method="post">
          <div class="d-flex flex-row align-items-center justify-content-center justify-content-lg-start mt-4 mb-4">
            <p class="lead fw-normal mb-0 me-3">Register with</p>
            <button type="button" class="btn btn-primary btn-floating mx-1"><i class="fab fa-facebook-f"></i></button>
            <button type="button" class="btn btn-primary btn-floating mx-1"><i class="fab fa-twitter"></i></button>
            <button type="button" class="btn btn-primary btn-floating mx-1"><i class="fab fa-linkedin-in"></i></button>
          </div>

          <div class="divider d-flex align-items-center my-4">
            <p class="text-center fw-bold mx-3 mb-0">Or</p>
          </div>

          <!-- Full Name input -->
          <div class="form-outline mb-4">
            <input type="text" id="form3Example1" class="form-control form-control-lg" name="fullName" placeholder="Enter your full name" required>
            <label class="form-label" for="form3Example1">Full Name</label>
          </div>

          <!-- Mobile Number input -->
          <div class="form-outline mb-4">
            <input type="text" id="form3Example2" class="form-control form-control-lg" name="mobile" placeholder="Enter your mobile number" required>
            <label class="form-label" for="form3Example2">Mobile Number</label>
          </div>

          <!-- City input -->
          <div class="form-outline mb-4">
            <input type="text" id="form3Example3" class="form-control form-control-lg" name="city" placeholder="Enter your city" required>
            <label class="form-label" for="form3Example3">City</label>
          </div>

          <!-- Email input -->
          <div class="form-outline mb-4">
            <input type="email" id="form3Example4" class="form-control form-control-lg" name="email" placeholder="Enter a valid email address" required>
            <label class="form-label" for="form3Example4">Email address</label>
          </div>

          <!-- Password input -->
          <div class="form-outline mb-3">
            <input type="password" id="form3Example5" class="form-control form-control-lg" name="password" placeholder="Enter password" required>
            <label class="form-label" for="form3Example5">Password</label>
          </div>

          <div class="text-center text-lg-start mt-4 pt-2">
            <button type="submit" class="btn btn-primary btn-lg" style="padding-left: 2.5rem; padding-right: 2.5rem;">Register</button>
            <p class="small fw-bold mt-2 pt-1 mb-0">Already have an account? <a href="login.jsp" class="link-danger">Login</a></p>
          </div>
        </form>
      </div>

      <!-- Footer -->
      <div class="container-fluid d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary">
        <div class="text-white mb-3 mb-md-0">Copyright © 2020. All rights reserved.</div>
        <div>
          <a href="#!" class="text-white me-4"><i class="fab fa-facebook-f"></i></a>
          <a href="#!" class="text-white me-4"><i class="fab fa-twitter"></i></a>
          <a href="#!" class="text-white me-4"><i class="fab fa-google"></i></a>
          <a href="#!" class="text-white"><i class="fab fa-linkedin-in"></i></a>
        </div>
      </div>
    </div>
  </div>
</section>

<!-- Bootstrap JS & Dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
