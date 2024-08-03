from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, NoSuchElementException
import time

# Initialize the WebDriver
driver = webdriver.Chrome()  # Ensure ChromeDriver is in your PATH
driver.maximize_window()

try:
    driver.get("https://www.fitpeo.com")  # Replace with the actual FitPeo Homepage URL

    revenue_calculator_link = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.LINK_TEXT, "Revenue Calculator"))  # Adjust the link text if necessary
    )
    revenue_calculator_link.click()

    slider_section = WebDriverWait(driver, 10).until(
        EC.visibility_of_element_located((By.ID, "slider-section-id"))  # Replace with actual ID or other locator
    )
    driver.execute_script("arguments[0].scrollIntoView();", slider_section)

    slider = WebDriverWait(driver, 10).until(
        EC.element_to_be_clickable((By.ID, "slider-id"))  # Replace with the actual ID of the slider
    )
    driver.execute_script("arguments[0].value = 820;", slider)
    time.sleep(1)  # Wait for the UI to update

    text_field = driver.find_element(By.ID, "text-field-id")  # Replace with actual ID
    text_field.clear()
    text_field.send_keys("560")
    text_field.send_keys(Keys.RETURN)

    slider_value = slider.get_attribute("value")
    assert slider_value == "560", f"Expected slider value 560 but got {slider_value}"

    checkbox_ids = ["cpt-99091", "cpt-99453", "cpt-99454", "cpt-99474"]  # Replace with actual IDs
    for checkbox_id in checkbox_ids:
        checkbox = driver.find_element(By.ID, checkbox_id)
        if not checkbox.is_selected():
            checkbox.click()

    total_reimbursement = driver.find_element(By.ID, "total-reimbursement-id")  # Replace with actual ID
    assert total_reimbursement.text == "$110700", f"Expected $110700 but got {total_reimbursement.text}"

    print("All test cases passed successfully!")

except (TimeoutException, NoSuchElementException) as e:
    print(f"An error occurred: {e}")
finally:
    # Close the WebDriver
    driver.quit()
