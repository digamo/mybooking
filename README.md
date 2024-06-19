# MyBooking API

## 📝 Table of Contents
- [About](#about)
- [Dependencies](#dependencies)
- [Database](#database)
- [API endpoints](#api)
- [DOCS](#docs)

## - About <a name = "about"></a>
API that has the functionality to create booking in a given property. 
At the same time, there is the possibility of creating blocks in accommodations according to the owner's wishes.

## - Dependencies <a name = "dependencies"></a>
- Compilation dependencies:
  - Java == jdk-17.0.11
  - Maven >= 3.6
  - Junit == 5
- Application dependent:
  - SpringBoot
  - H2 Database
  - Lombok

## - Database <a name = "database"></a>

The schema with the tables used in the project is in the **\resource\schema.sql** file

## - API endpoints <a name = "api"></a>

In each creation or update request, the audit date is recorded in the "insertAt" and "updateAt" fields.
Deletions were set to physical in the database.

## /block

### GET `/block/<id>`
Search for a block from the ID

**Response**
```
{
	"insertAt": "2024-06-19T12:34:31.7902563",
	"updateAt": "2024-06-19T12:34:31.7902563",
	"id": 1,
	"startDate": "2014-07-01",
	"endDate": "2014-07-02",
	"reason": "House will be under renovation."
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}

```

### POST `/block`
Create a block

**Body**
```
{
	"startDate": "2014-07-01",
	"endDate": "2014-07-02",
	"reason": "House will be under renovation",
	"property": {"id": 1 , "name": "Test Property A"}
}

```

**Response**
```
{
	"insertAt": "2024-06-19T12:34:31.7902563",
	"updateAt": "2024-06-19T12:34:31.7902563",
	"id": 1,
	"startDate": "2014-07-01",
	"endDate": "2014-07-02",
	"reason": "House will be under renovation."
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}

```

### PUT `/block/<id>`
Update a block from an ID

**Body**
```
{
	"startDate": "2014-07-10",
	"endDate": "2014-07-20",
	"reason": "House will be under renovation",
	"property": {"id": 1 , "name": "Test Property A"}
}

```

**Response**
```
{
	"insertAt": "2024-06-19T12:34:31.790256",
	"updateAt": "2024-06-19T12:36:45.2914843",
	"id": 1,
	"startDate": "2014-07-10",
	"endDate": "2014-07-20",
	"reason": "House will be under renovation",
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}
```

### DELETE `/block/<id>`
Delete a block from an ID

## /booking

### GET `/booking/<id>`
Search for a booking from the ID

**Response**
```
{
	"insertAt": "2024-06-19T12:38:07.4228435",
	"updateAt": "2024-06-19T12:38:07.4228435",
	"id": 1,
	"startDate": "2014-09-10",
	"endDate": "2014-09-15",
	"status": "BOOKED",
	"details": "I will need one more bed.",
	"guest": {
		"id": 1,
		"name": "Diego Gomes"
	},
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}
```
### POST `/booking`
Create a booking

**Body**
```
{
	"startDate": "2014-09-10",
	"endDate": "2014-09-15",
	"details": "I will need one more bed.",
	"guest": {"id": 1, "name": "Diego Gomes"},
	"property": {"id": 1 , "name": "Test Property A"}
}

```

**Response**
```
{
	"insertAt": "2024-06-19T12:38:07.4228435",
	"updateAt": "2024-06-19T12:38:07.4228435",
	"id": 1,
	"startDate": "2014-09-10",
	"endDate": "2014-09-15",
	"status": "BOOKED",
	"details": "I will need one more bed.",
	"guest": {
		"id": 1,
		"name": "Diego Gomes"
	},
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}
```

### PUT `/booking/<id>`
Update a booking from an ID

**Body**
```
{
	"startDate": "2014-09-20",
	"endDate": "2014-09-25",
	"details": "I will need two more bed.",
	"guest": {"id": 1, "name": "Diego Gomes"},
	"property": {"id": 1 , "name": "Test Property A"}
}


```

**Response**
```
{
	"insertAt": "2024-06-19T12:33:25.300238",
	"updateAt": "2024-06-19T12:39:09.9928967",
	"id": 1,
	"startDate": "2014-09-20",
	"endDate": "2014-09-25",
	"status": "BOOKED",
	"details": "I will need two more bed.",
	"guest": {
		"id": 1,
		"name": "Diego Gomes"
	},
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}
```

### PUT `/booking/<id>/cancel`
Update a booking to a CANCELED status from an ID

**Response**
```
{
	"insertAt": "2024-06-19T12:33:25.300238",
	"updateAt": "2024-06-19T12:39:40.3246084",
	"id": 1,
	"startDate": "2014-09-20",
	"endDate": "2014-09-25",
	"status": "CANCELED",
	"details": "I will need two more bed.",
	"guest": {
		"id": 1,
		"name": "Diego Gomes"
	},
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}
```

### PUT `/booking/<id>/reschedule`
After a canceled booking, it is BOOKED again with a call to /reschedule from an ID

**Response**
```
{
	"insertAt": "2024-06-19T12:33:25.300238",
	"updateAt": "2024-06-19T12:39:54.8554304",
	"id": 1,
	"startDate": "2014-09-20",
	"endDate": "2014-09-25",
	"status": "BOOKED",
	"details": "I will need two more bed.",
	"guest": {
		"id": 1,
		"name": "Diego Gomes"
	},
	"property": {
		"id": 1,
		"name": "Test Property A"
	}
}
```
### DELETE `/booking/<id>`
Delete a booking from an ID



### - OpenAPI REST Docs <a name = "docs"></a>

Available in [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
