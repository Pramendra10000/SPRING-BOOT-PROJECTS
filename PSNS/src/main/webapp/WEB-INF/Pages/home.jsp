<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PSNS Bank | Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5.2 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #0d6efd, #003a8f);
        }

        .login-container {
            max-width: 1100px;
        }
    </style>
</head>
<body>

<div class="container d-flex align-items-center justify-content-center min-vh-100">
    <div class="row w-100 shadow-lg rounded-4 overflow-hidden bg-white login-container">

        <!-- LEFT PANEL (Branding) -->
        <div class="col-lg-6 d-none d-lg-flex flex-column justify-content-center p-5 text-white"
             style="background: linear-gradient(135deg, #0d6efd, #0047ab);">

            <div class="mb-4">
                <i class="bi bi-wallet2 fs-1"></i>
                <h2 class="fw-bold mt-3">PSNS BANK</h2>
                <p class="mt-2">Secure. Reliable. Modern Banking.</p>
            </div>

            <ul class="list-unstyled mt-4 fs-5">
                <li class="mb-3">
                    <i class="bi bi-credit-card me-2"></i> Digital Banking
                </li>
                <li class="mb-3">
                    <i class="bi bi-clock-history me-2"></i> 24/7 Access
                </li>
                <li>
                    <i class="bi bi-send me-2"></i> Instant Transfers
                </li>
            </ul>
        </div>

        <!-- RIGHT PANEL (Login Form) -->
        <div class="col-lg-6 p-5">

            <h2 class="fw-bold">Welcome Back</h2>
            <p class="text-muted mb-4">Please login to your account</p>

            <form method="post" action="#">
                <!-- Username -->
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" class="form-control form-control-lg"
                           placeholder="Enter your username" required>
                </div>

                <!-- Password -->
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control form-control-lg"
                               placeholder="Enter your password" required>
                        <span class="input-group-text bg-white">
                            <i class="bi bi-eye"></i>
                        </span>
                    </div>
                </div>

                <!-- Remember -->
                <div class="form-check mb-4">
                    <input class="form-check-input" type="checkbox" id="rememberMe">
                    <label class="form-check-label" for="rememberMe">
                        Remember me
                    </label>
                </div>

                <!-- Login -->
                <button type="submit" class="btn btn-primary btn-lg w-100">
                    Login
                </button>

                <!-- Forgot -->
                <div class="text-center mt-3">
                    <a href="#" class="text-decoration-none">Forgot Password?</a>
                </div>

            </form>

        </div>

    </div>
</div>

</body>
</html>
