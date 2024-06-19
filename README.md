# MyBooking API

## 📝 Table of Contents
- [About](#about)
- [Dependencies](#dependencies)
- [API endpoints](#api)


## 🧐 About <a name = "about"></a>
API that has the functionality to create booking in a given property. 
At the same time, there is the possibility of creating blocks in accommodations according to the owner's wishes.

## ⛓️️ Dependencies <a name = "dependencies"></a>
- Compilation dependencies:
- Java == jdk-17.0.11
- Maven >= 3.6
- Junit == 5
- Application dependent:
- SpringBoot
- H2 Database
- Lombok

## 🎈 API endpoints <a name = "api"></a>

# /block

### GET `/block/<id>`
Search for a block from the ID

**Response**
```
{
	"insertAt": "2024-06-17T23:29:46.1965448",
	"updateAt": "2024-06-17T23:29:46.1965448",
	"id": 1,
	"startDate": "2014-06-12",
	"endDate": "2014-06-14",
	"reason": "House will be under renovation."
}
```

### POST `/block`
Create a block

**Body**
```
{
	"startDate": "2014-06-12",
	"endDate": "2014-06-14",
	"reason": "House will be under renovation",
	"property": {"id": 1}
}

```

**Response**
```
{
"insertAt": "2024-06-17T23:29:46.1965448",
"updateAt": "2024-06-17T23:29:46.1965448",
"id": 1,
"startDate": "2014-06-12",
"endDate": "2014-06-14",
"reason": "House will be under renovation."
}
```

### PUT `/block/<id>`
Update a block from an ID

**Body**
```
{
"startDate": "2014-06-15",
"endDate": "2014-06-20",
"reason": "House will be under renovation",
"property": {"id": 1}
}
```

**Response**
```
{
"insertAt": "2024-06-17T23:29:46.1965448",
"updateAt": "2024-06-17T23:29:46.1965448",
"id": 1,
"startDate": "2014-06-15",
"endDate": "2014-06-20",
"reason": "House will be under renovation."
}
```

### DELETE `/block/<id>`
Delete a block from an ID

# /booking

### GET `/booking/<id>`
Search for a booking from the ID

**Response**
```
{
	"insertAt": "2024-06-18T12:18:08.609601",
	"updateAt": "2024-06-18T12:18:08.609601",
	"id": 1,
	"startDate": "2014-07-10",
	"endDate": "2014-07-15",
	"status": "BOOKED",
	"details": "I will need one more bed."
}
```
### POST `/booking`
Create a booking

**Body**
```
{
	"startDate": "2014-07-10",
	"endDate": "2014-07-15",
	"details": "I will need one more bed.",
	"guest": {"id": 1},
	"property": {"id": 2}
}

```

**Response**
```
{
	"insertAt": "2024-06-18T12:18:08.6096009",
	"updateAt": "2024-06-18T12:18:08.6096009",
	"id": 1,
	"startDate": "2014-07-10",
	"endDate": "2014-07-15",
	"status": "BOOKED",
	"details": "I will need one more bed."
}
```

### PUT `/booking/<id>`
Update a booking from an ID

**Body**
```
{
	"startDate": "2014-07-01",
	"endDate": "2014-07-05",
	"details": "I will need two more bed."
	"guest": {"id": 1},
	"property": {"id": 1}
}


```

**Response**
```
{
	"insertAt": "2024-06-17T23:29:56.586945",
	"updateAt": "2024-06-17T23:30:19.5734143",
	"id": 1,
	"startDate": "2014-07-01",
	"endDate": "2014-07-05",
	"status": "BOOKED",
	"details": "I will need two more bed."
}
```

### PUT `/booking/<id>/cancel`
Update a booking to a CANCELED status from an ID

**Response**
```
{
	"insertAt": "2024-06-17T23:29:56.586945",
	"updateAt": "2024-06-17T23:30:19.5734143",
	"id": 1,
	"startDate": "2014-07-01",
	"endDate": "2014-07-05",
	"status": "CANCELED",
	"details": "I will need two more bed."
}
```

### PUT `/booking/<id>/reschedule`
After a canceled booking, it is BOOKED again with a call to /reschedule from an ID

**Response**
```
{
	"insertAt": "2024-06-17T23:29:56.586945",
	"updateAt": "2024-06-17T23:30:19.5734143",
	"id": 1,
	"startDate": "2014-07-01",
	"endDate": "2014-07-05",
	"status": "BOOKED",
	"details": "I will need two more bed."
}
```
### DELETE `/booking/<id>`
Delete a booking from an ID
