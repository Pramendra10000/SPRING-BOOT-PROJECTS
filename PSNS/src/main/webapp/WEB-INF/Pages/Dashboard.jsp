<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PSNS Bank | Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5.2 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="Css/Dashboard.css" rel="stylesheet">

	<script src="/JavaScript/DashboardJS.js" defer></script>

</head>
<body>

<!-- SIDEBAR -->
<div class="sidebar">
    <div class="brand">
        <i class="bi bi-credit-card-2-front"></i> PSNS BANK
    </div>

	<a href="/dashboard" class="active"><i class="bi bi-house"></i> Dashboard</a>
	   <a href="/transfer"><i class="bi bi-send"></i> Transfer Money</a>
	   <a href="/transaction" ><i class="bi bi-clock-history"></i> Transactions</a>
	   <a href="/account"><i class="bi bi-file-text"></i> Account Details</a>
	   <a href="/profile"><i class="bi bi-person"></i> Profile</a>

    <div class="logout">
        <a href="/logout" class="text-danger">
            <i class="bi bi-box-arrow-right"></i> Logout
        </a>
    </div>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">

    <!-- TOP BAR -->
    <nav class="navbar bg-white shadow-sm px-4">
        <span class="navbar-brand mb-0 h5">Dashboard</span>
        <div class="d-flex align-items-center gap-2">
            <i class="bi bi-person-circle fs-3 text-primary"></i>
			<h4 class="mb-0">${dashboard.userName}</h4>
</div>
    </nav>

    <div class="container-fluid p-4">

        <div class="row g-4">

            <!-- BALANCE -->
            <div class="col-lg-8">
                <div class="balance-card p-4">
					<h6>Total Balance</h6>
					<h2 class="fw-bold">$${dashboard.totalBalance}</h2>
					<p class="mb-0">Account: ${dashboard.accountNumber}</p>


                </div>

                <!-- QUICK ACTIONS -->
                <div class="bg-white rounded-4 p-4 shadow-sm mt-4">
                    <h6 class="mb-3">Quick Actions</h6>
                    <div class="row g-3">
                        <div class="col-md-3 col-6">
                            <div class="quick-action">
                                <i class="bi bi-send fs-3"></i>
                                <p class="mt-2 mb-0">Send Money</p>
                            </div>
                        </div>
                        <div class="col-md-3 col-6">
                            <div class="quick-action">
                                <i class="bi bi-arrow-down-left fs-3"></i>
                                <p class="mt-2 mb-0">Request Money</p>
                            </div>
                        </div>
                        <div class="col-md-3 col-6">
                            <div class="quick-action">
                                <i class="bi bi-receipt fs-3"></i>
                                <p class="mt-2 mb-0">Pay Bills</p>
                            </div>
                        </div>
                        <div class="col-md-3 col-6">
                            <div class="quick-action">
                                <i class="bi bi-plus-square fs-3"></i>
                                <p class="mt-2 mb-0">Add Money</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- TRANSACTIONS -->
            <div class="col-lg-4">
                <div class="bg-white rounded-4 p-4 shadow-sm">
                    <h6 class="mb-3">Recent Transactions</h6>

					<ul class="list-unstyled">
					    <c:forEach var="txn" items="${dashboard.transactions}">
					        <li class="txn ${txn.type}">
					            <div>
					                <strong>${txn.title}</strong><br>
					                <small>${txn.date}</small>
					            </div>
					            <strong>${txn.amount}</strong>
					        </li>
					    </c:forEach>
					</ul>


                   

                    <button class="btn btn-outline-primary w-100 mt-3">
                        View All
                    </button>
                </div>
            </div>

        </div>
    </div>
</div>


</body>
</html>
