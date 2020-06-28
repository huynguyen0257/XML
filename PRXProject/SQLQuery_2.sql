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

-- Create a new table called '[Laptop]' in schema '[dbo]'
-- Drop the table if it already exists
IF OBJECT_ID('[dbo].[Laptop]', 'U') IS NOT NULL
DROP TABLE [dbo].[Laptop]
GO
-- Create the table in the specified schema
CREATE TABLE [dbo].[Laptop]
(
    [Id] INT NOT NULL IDENTITY(1,1) PRIMARY KEY, -- Primary Key column
    [Model] NVARCHAR(50) NOT NULL,
    [Price] DECIMAL NOT NULL,
    [Brand] NVARCHAR(50) NOT NULL,
    [CPU] NVARCHAR(50) NOT NULL,
    [VGA] NVARCHAR(50),
    [RAM] NVARCHAR(50),
    [HardDisk] NVARCHAR(50),
    [LCD] NVARCHAR(50),
    [Options] NVARCHAR(50),
    [Port] NVARCHAR(50),
    [Webcam] BIT NOT NULL,
    [FingerprintRecognition] BIT NOT NULL,
    [FaceRecognition] BIT NOT NULL,
    [OS] NVARCHAR(50),
    [Battery] NVARCHAR(50),
    [Size] NVARCHAR(50),
    [Weight] NVARCHAR(50),
    [Color] NVARCHAR(50),
    -- Specify more columns here
);
GO

-- Insert rows into table '' in schema '[dbo]'
INSERT INTO [dbo].[Laptop]
( -- Columns to insert data into
 [Model], [Price], [Brand],[CPU],Webcam,FingerprintRecognition,FaceRecognition
)
VALUES
( -- First row: values for the columns in the list above
 'test', 123126794, 'Lenovo','i5',0,0,0
)
-- Add more rows here
GO

-- Select rows from a Table or View '[Laptop]' in schema '[dbo]'
SELECT * FROM [dbo].[Laptop]
GO 

-- Delete rows from table '[Laptop]' in schema '[dbo]'
DELETE FROM [dbo].[Laptop]
GO