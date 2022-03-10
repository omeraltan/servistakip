import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class PersonelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.personel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  adiInput: ElementFinder = element(by.css('input#personel-adi'));
  soyadiInput: ElementFinder = element(by.css('input#personel-soyadi'));
  telefonuInput: ElementFinder = element(by.css('input#personel-telefonu'));
  epostaInput: ElementFinder = element(by.css('input#personel-eposta'));
  unvaniSelect: ElementFinder = element(by.css('select#personel-unvani'));

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
    await this.setEpostaInput('eposta');
    await waitUntilDisplayed(this.saveButton);
    await this.unvaniSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
