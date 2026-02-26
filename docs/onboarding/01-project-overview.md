# Project Overview

## What is Shopizer?

Shopizer is an **open-source, headless e-commerce platform** built with Java and Spring Boot. It provides a complete backend solution for building modern online stores with decoupled frontends.

## Key Features

### 🛍️ E-commerce Core
- **Product Catalog Management**
  - Products with variants and attributes
  - Categories (hierarchical)
  - Manufacturers/Brands
  - Product images and descriptions
  - Inventory management
  - Pricing (regular, sale, bulk)

- **Shopping Experience**
  - Shopping cart management
  - Checkout process
  - Order management
  - Customer accounts
  - Product reviews and ratings
  - Wishlist support

- **Payment & Shipping**
  - Multiple payment methods
  - Shipping options and quotes
  - Tax calculation
  - Order tracking

### 🏪 Multi-Store Support
- Multiple merchant stores on one platform
- Store-specific configurations
- Isolated data per store

### 👥 User Management
- Customer accounts
- Admin users with role-based access
- Permission management
- JWT-based authentication

### 🔍 Search & Discovery
- Elasticsearch integration
- Full-text product search
- Faceted search
- Category browsing

### 📄 Content Management
- Static content pages
- Image management
- File storage (local or cloud)

## Architecture Type

**Headless Commerce** - The backend (Shopizer) is completely decoupled from the frontend. You can build:
- Web applications (React, Angular, Vue)
- Mobile apps (iOS, Android)
- Progressive Web Apps (PWA)
- IoT applications

All communication happens via REST APIs.

## Use Cases

Shopizer is suitable for:
- Small to medium-sized online stores
- Multi-vendor marketplaces
- B2C e-commerce platforms
- B2B commerce solutions
- Custom commerce applications

## Project Stats

- **Version**: 3.2.5 (as of this documentation)
- **License**: Apache License 2.0
- **Language**: Java
- **Framework**: Spring Boot
- **First Release**: 2010+
- **Active Development**: Yes

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Frontend Layer                          │
│  (React/Angular/Vue/Mobile - Not included in this repo)    │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ REST API (JSON)
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   Shopizer Backend (sm-shop)                │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │ REST APIs    │  │  Security    │  │   Swagger    │     │
│  │ (v1, v2)     │  │  (JWT)       │  │   Docs       │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              Business Logic Layer (sm-core)                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │  Services    │  │ Repositories │  │   Modules    │     │
│  │  (Business)  │  │  (Data)      │  │ (Integrations)│    │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   Data Layer (sm-core-model)                │
│              JPA Entities & Domain Models                   │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                      Database Layer                         │
│         MySQL / PostgreSQL / H2 / Oracle                    │
└─────────────────────────────────────────────────────────────┘
```

## External Integrations

```
┌─────────────────────────────────────────────────────────────┐
│                    Shopizer Core                            │
└─────────────────────────────────────────────────────────────┘
         │              │              │              │
         ▼              ▼              ▼              ▼
    ┌────────┐    ┌────────┐    ┌────────┐    ┌────────┐
    │Payment │    │Shipping│    │ Email  │    │  CMS   │
    │Gateway │    │Provider│    │Service │    │Storage │
    └────────┘    └────────┘    └────────┘    └────────┘
    PayPal        Canada Post   SMTP          AWS S3
    Stripe        FedEx         SendGrid      GCP Storage
    etc.          etc.          etc.          Local FS
```

## Why Shopizer?

### Pros
✅ **Open Source** - Free to use and modify
✅ **Headless** - Build any frontend you want
✅ **Java/Spring** - Enterprise-grade, mature ecosystem
✅ **Feature-Rich** - Complete e-commerce functionality
✅ **Multi-Store** - Support multiple merchants
✅ **Extensible** - Plugin architecture for integrations
✅ **REST APIs** - Well-documented with Swagger

### Considerations
⚠️ **Learning Curve** - Large codebase, requires Java knowledge
⚠️ **Legacy Patterns** - Mix of old and new code patterns
⚠️ **Documentation** - Some areas need better docs
⚠️ **Frontend Separate** - You need to build your own UI

## Next Steps

Continue to [Tech Stack](02-tech-stack.md) to understand the technologies used.
