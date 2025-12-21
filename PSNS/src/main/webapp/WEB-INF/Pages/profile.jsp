<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PSNS Bank | Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
	    setTimeout(() => {
	        const alert = document.querySelector('.alert');
	        if (alert) {
	            alert.classList.remove('show');
	        }
	    }, 3000);
	</script>

    <!-- Dashboard + Profile CSS -->
    <link href="Css/Dashboard.css" rel="stylesheet">
    <link href="Css/Profile.css" rel="stylesheet">
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
    <a href="/account"><i class="bi bi-file-text"></i> Account Details</a>
    <a href="/profile" class="active"><i class="bi bi-person"></i> Profile</a>

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
        <span class="navbar-brand mb-0 h5">Profile Settings</span>
        <div class="d-flex align-items-center gap-2">
            <i class="bi bi-person-circle fs-3 text-primary"></i>
            <h5 class="mb-0">${profile.userName}</h5>
        </div>
    </nav>

    <!-- CONTENT -->
    <div class="container-fluid p-4">

		<!-- ✅ SUCCESS MESSAGE -->
		<c:if test="${param.success ne null}">
		    <div class="alert alert-success alert-dismissible fade show mb-4" role="alert">
		        <i class="bi bi-check-circle-fill me-2"></i>
		        Profile updated successfully!
		        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
		    </div>
		</c:if>

		
        <div class="row justify-content-center">
            <div class="col-lg-7">

                <div class="profile-card bg-white shadow-sm rounded-4 p-4">

                    <!-- Profile Icon -->
                    <div class="text-center mb-4">
                        <div class="profile-avatar mx-auto mb-2">
                            <i class="bi bi-person"></i>
                        </div>
                        <h5 class="mb-0">${profile.firstName} ${profile.lastName}</h5>
                        <small class="text-muted">${profile.email}</small>
                    </div>

                    <!-- Profile Form -->
                    <form action="/profile/update" method="post">

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label">First Name</label>
                                <input type="text" class="form-control"
                                       name="firstName"
                                       value="${profile.firstName}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Last Name</label>
                                <input type="text" class="form-control"
                                       name="lastName"
                                       value="${profile.lastName}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" class="form-control"
                                       name="email"
                                       value="${profile.email}">
                            </div>

                            <div class="col-md-6">
                                <label class="form-label">Phone</label>
                                <input type="text" class="form-control"
                                       name="phone"
                                       value="${profile.phone}">
                            </div>

                            <div class="col-12">
                                <label class="form-label">Address</label>
                                <textarea class="form-control" rows="3"
                                          name="address">${profile.address}</textarea>
                            </div>
                        </div>

                        <!-- Buttons -->
                        <div class="d-flex gap-3 mt-4">
                            <button type="submit" class="btn btn-primary px-4">
                                Update Profile
                            </button>
                            <a href="/dashboard" class="btn btn-outline-secondary px-4">
                                Cancel
                            </a>
                        </div>

                    </form>
                </div>

            </div>
        </div>

    </div>
</div>


</body>
</html>
