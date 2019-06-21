-- -----------------------------------------------------
-- Table `wallethub`.`logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wallethub`.`logs` ;

CREATE TABLE IF NOT EXISTS `wallethub`.`logs` (
  `date` VARCHAR(45) NOT NULL,
  `ip` VARCHAR(15) NOT NULL,
  `request` VARCHAR(250) NOT NULL,
  `status` INT NOT NULL,
  `user_agent` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`date`, `ip`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;