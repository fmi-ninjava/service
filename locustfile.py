from locust import FastHttpUser, task
from faker import Faker
from faker.providers import internet


class HelloWorldUser(FastHttpUser):
    customerId = None
    customerWebsite = None
    fake = Faker()

    def on_start(self):
        self.fake.add_provider(internet)
        createCustomerResponse = self.client.post(
            "/customers", json={"name": self.fake.name()}
        ).json()
        self.customerId = createCustomerResponse["customerId"]
        print(self.customerId)

        self.customerWebsite = self.fake.url()
        self.client.post(
            f"/customers/{self.customerId}/websites",
            json={"baseUrl": self.customerWebsite},
        )

    @task(10)
    def visit_website(self):
        self.client.post(
            "/page-visit",
            json={"baseUrl": self.customerWebsite, "urlPath": "Home/Index"},
        )

    @task
    def get_report(self):
        self.client.get(f"/customers/page-visit/{self.customerId}")

    @task
    def get_client_list(self):
        self.client.get("/customers")
