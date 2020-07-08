-- Drop the database 'PRXProject'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Uncomment the ALTER DATABASE statement below to set the database to SINGLE_USER mode if the drop database command fails because the database is in use.
-- ALTER DATABASE PRXProject SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
-- Drop the database if it exists
IF EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'PRXProject'
)
DROP DATABASE PRXProject
GO 


-- Create a new database called 'PRXProject'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Create the new database if it does not exist already
IF NOT EXISTS (
    SELECT [name]
        FROM sys.databases
        WHERE [name] = N'PRXProject'
)
CREATE DATABASE PRXProject
GO

USE PRXProject
GO

-- Create a new table called '[Brand]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Brand]', 'U') IS NOT NULL
DROP TABLE [dbo].[Brand]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Brand]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Name] NVARCHAR(50) NOT NULL
    -- Specify more columns here
);
GO



-- Create a new table called '[Processor]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Processor]', 'U') IS NOT NULL
DROP TABLE [dbo].[Processor]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Processor]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Brand] NVARCHAR(50) NOT NULL,
    [Name] NVARCHAR(50) NOT NULL,
    [Model] NVARCHAR(50) NOT NULL,
    [Core] INT NOT NULL,
    [Thread] INT NOT NULL,
    [BaseClock] FLOAT NOT NULL,
    [BoostClock] FLOAT NOT NULL,
    [Cache] FLOAT NOT NULL,
    [Mark] INT NOT NULL
    -- Specify more columns here
);
GO




-- Create a new table called '[Laptop]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Laptop]', 'U') IS NOT NULL
DROP TABLE [dbo].[Laptop]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Laptop]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Name] NVARCHAR(500) NOT NULL,
    [Model] NVARCHAR(500) NOT NULL,
    [Price] INT NOT NULL,
    [VGA] NVARCHAR(500),
    [RAM] NVARCHAR(500),
    [HardDisk] NVARCHAR(500),
    [LCD] NVARCHAR(500),
    [Options] NVARCHAR(500),
    [Port] NVARCHAR(500),
    [OS] NVARCHAR(500),
    [Battery] NVARCHAR(500),
    [Weight] NVARCHAR(500),
    [Color] NVARCHAR(500),
    [BrandId] int NOT NULL,
    [ProcessorId] int NOT NULL
    -- Specify more columns here
);
GO
ALTER TABLE Laptop ADD CONSTRAINT FK_Brand FOREIGN KEY (BrandId) references Brand(Id)  
ALTER TABLE Laptop ADD CONSTRAINT FK_Processor FOREIGN KEY (ProcessorId) references Processor(Id)




































SELECT COUNT(Model)
FROM Laptop

SELECT COUNT(Model)
FROM Processor

-- Delete rows from table '[Brand]' in schema '[dbo]'
DELETE FROM [dbo].[Brand]
GO

-- Delete rows from table '[Laptop]' in schema '[dbo]'
DELETE FROM [dbo].[Laptop]
GO

-- Delete rows from table '[Laptop]' in schema '[dbo]'
DELETE FROM [dbo].[Processor]
-- WHERE Brand = 'AMD'
GO
