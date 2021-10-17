PRAGMA foreign_keys = OFF;
drop table if exists PopulationStatistics;
PRAGMA foreign_keys = ON;

CREATE TABLE PopulationStatistics (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    age               TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, age)
    FOREIGN KEY (lga_code16) REFERENCES LGAs(lga_code16)
);

-- employment table
PRAGMA foreign_keys = OFF;
drop table if exists EmploymentStatistics;
PRAGMA foreign_keys = ON;

CREATE TABLE EmploymentStatistics (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    Labour_force      TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, Labour_force)
    FOREIGN KEY (lga_code16) REFERENCES LGAs(lga_code16)
);

-- school table
PRAGMA foreign_keys = OFF;
drop table if exists SchoolStatistics;
PRAGMA foreign_keys = ON;

CREATE TABLE SchoolStatistics (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    School            TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, School)
    FOREIGN KEY (lga_code16) REFERENCES LGAs(lga_code16)
);

-- qualification table
PRAGMA foreign_keys = OFF;
drop table if exists QualificationStatistics;
PRAGMA foreign_keys = ON;

CREATE TABLE QualificationStatistics (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    Qualification      TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, Qualification)
    FOREIGN KEY (lga_code16) REFERENCES LGAs(lga_code16)
);
