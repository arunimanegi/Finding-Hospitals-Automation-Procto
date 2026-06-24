"# Findind_Hospital_CTS" 
# Finding-Hospitals-Automation-Procto

## 📋 Problem Statement
The objective of this automation project is to scrape, filter, and interact with hospital and diagnostic data from healthcare portals (e.g., [Practo](https://www.practo.com) or any other legitimate site). The project automates search filters, extracts list elements, handles alerts, and validates input forms to demonstrate comprehensive web automation capabilities.

## 🔍 Detailed Requirements

### 1. Hospital Filtering & Extraction
* **Target City:** Bangalore
* **Criteria:** * Open 24/7
  * Has Parking facilities
  * User rating greater than **3.5**
* **Action:** Search for these hospitals and display/log the names of all matching facilities.

### 2. Diagnostic Cities Extraction
* **Navigation:** Navigate to the **Diagnostics** section of the portal.
* **Action:** Pick all top cities listed on the page, store them in a Collection (List), and display/log them.

### 3. Corporate Wellness Form Validation
* **Navigation:** Navigate to the **Corporate Wellness** section.
* **Action:** Fill out the corporate form using **invalid details** and attempt to schedule.
* **Validation:** Capture, display, and verify the resulting alert warning message.

---

## ⚙️ Key Automation Scope
This project covers advanced web automation scenarios, including:
* **UI Controls & Interactivity:** Handling dynamic dropdowns, checkboxes (Open 24/7, Parking), and search functionalities.
* **Window & Navigation Management:** Switching between different browser windows/tabs and seamless navigation back to the home page.
* **Data Extraction & Storage:** Scraping multiple element items from lists/grids and organizing them into programming collections (e.g., Lists, Arrays).
* **Form Automation:** Interacting with complex form fields across diverse web objects.
* **Pop-ups & Alerts:** Catching browser alerts/dialog boxes and extracting text messages for negative test case validation.

---

## 🛠️ Tech Stack & Prerequisites
* **Language:** Java / Python / C#
* **Framework:** Selenium WebDriver / Playwright
* **Test Runner:** TestNG / JUnit / PyTest
* **Design Pattern:** Page Object Model (POM) recommended