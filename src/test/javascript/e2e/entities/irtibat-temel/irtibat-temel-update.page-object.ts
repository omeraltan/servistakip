import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class IrtibatTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.irtibatTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  adiInput: ElementFinder = element(by.css('input#irtibat-temel-adi'));
  soyadiInput: ElementFinder = element(by.css('input#irtibat-temel-soyadi'));
  telefonuInput: ElementFinder = element(by.css('input#irtibat-temel-telefonu'));
  tcnuInput: ElementFinder = element(by.css('input#irtibat-temel-tcnu'));
  epostaInput: ElementFinder = element(by.css('input#irtibat-temel-eposta'));
  unvaniSelect: ElementFinder = element(by.css('select#irtibat-temel-unvani'));
  kurumSelect: ElementFinder = element(by.css('select#irtibat-temel-kurum'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAdiInput(adi) {
    await this.adiInput.sendKeys(adi);
  }

  async getAdiInput() {
    return this.adiInput.getAttribute('value');
  }

  async setSoyadiInput(soyadi) {
    await this.soyadiInput.sendKeys(soyadi);
  }

  async getSoyadiInput() {
    return this.soyadiInput.getAttribute('value');
  }

  async setTelefonuInput(telefonu) {
    await this.telefonuInput.sendKeys(telefonu);
  }

  async getTelefonuInput() {
    return this.telefonuInput.getAttribute('value');
  }

  async setTcnuInput(tcnu) {
    await this.tcnuInput.sendKeys(tcnu);
  }

  async getTcnuInput() {
    return this.tcnuInput.getAttribute('value');
  }

  async setEpostaInput(eposta) {
    await this.epostaInput.sendKeys(eposta);
  }

  async getEpostaInput() {
    return this.epostaInput.getAttribute('value');
  }

  async setUnvaniSelect(unvani) {
    await this.unvaniSelect.sendKeys(unvani);
  }

  async getUnvaniSelect() {
    return this.unvaniSelect.element(by.css('option:checked')).getText();
  }

  async unvaniSelectLastOption() {
    await this.unvaniSelect.all(by.tagName('option')).last().click();
  }
  async kurumSelectLastOption() {
    await this.kurumSelect.all(by.tagName('option')).last().click();
  }

  async kurumSelectOption(option) {
    await this.kurumSelect.sendKeys(option);
  }

  getKurumSelect() {
    return this.kurumSelect;
  }

  async getKurumSelectedOption() {
    return this.kurumSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setAdiInput('adi');
    await waitUntilDisplayed(this.saveButton);
    await this.setSoyadiInput('soyadi');
    await waitUntilDisplayed(this.saveButton);
    await this.setTelefonuInput('telefonu');
    await waitUntilDisplayed(this.saveButton);
    await this.setTcnuInput('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setEpostaInput('eposta');
    await waitUntilDisplayed(this.saveButton);
    await this.unvaniSelectLastOption();
    await this.kurumSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
