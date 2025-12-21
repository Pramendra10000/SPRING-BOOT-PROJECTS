<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PSNS Bank | Account Details</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5.2 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Unified CSS -->
    <link href="Css/AccountDetails.css" rel="stylesheet">

    <script src="/JavaScript/DashboardJS.js" defer></script>
</head>
<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <div class="brand">
        <i class="bi bi-credit-card-2-front"></i> PSNS BANK
    </div>

    <a href="/dashboard"><i class="bi bi-house"></i> Dashboard</a>
    <a href="/transfer"><i class="bi bi-send"></i> Transfer Money</a>
    <a href="/transaction"><i class="bi bi-clock-history"></i> Transactions</a>
    <a href="/account" class="active"><i class="bi bi-file-text"></i> Account Details</a>
    <a href="/profile"><i class="bi bi-person"></i> Profile</a>

    <div class="logout">
        <a href="/logout" class="text-danger">
            <i class="bi bi-box-arrow-right"></i> Logout
        </a>
    </div>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <!-- HEADER / TOP NAV -->
    <nav class="navbar">
        <span class="navbar-brand mb-0 h5">Account Details</span>
        <div class="d-flex align-items-center gap-2">
            <i class="bi bi-person-circle"></i>
            <h4 class="mb-0">John Doe</h4>
        </div>
    </nav>

    <div class="container-fluid p-4">
        <div class="row g-4">

            <!-- Account Information -->
            <div class="col-lg-7">
                <div class="card-box">
                    <h5 class="mb-4">Account Information</h5>

                    <p class="text-muted mb-1">ACCOUNT NUMBER</p>
                    <h6>**** **** **** 4782</h6>
                    <hr>

                    <p class="text-muted mb-1">ACCOUNT TYPE</p>
                    <h6>Savings Account</h6>
                    <hr>

                    <p class="text-muted mb-1">ACCOUNT STATUS</p>
                    <span class="status-badge">Active</span>
                    <hr>

                    <p class="text-muted mb-1">BRANCH</p>
                    <h6>PSNS Main Branch</h6>
                    <hr>

                    <p class="text-muted mb-1">IFSC CODE</p>
                    <h6>PSNS0001234</h6>
                </div>
            </div>

            <!-- Card Details -->
            <div class="col-lg-5">
                <div class="card-box">
                    <h5 class="mb-3">Card Details</h5>

                    <div class="bank-card mb-3">
                        <div class="chip"></div>
                        <h5>**** **** **** 4782</h5>
                        <div class="d-flex justify-content-between mt-4">
                            <div>
                                <small>CARD HOLDER</small>
                                <h6>JOHN DOE</h6>
                            </div>
                            <div>
                                <small>EXPIRES</small>
                                <h6>12/26</h6>
                            </div>
                        </div>
                    </div>

                    <button class="btn btn-outline-primary w-100">
                        Request New Card
                    </button>
                </div>
            </div>

        </div>
    </div>

</div>

</body>
</html>
