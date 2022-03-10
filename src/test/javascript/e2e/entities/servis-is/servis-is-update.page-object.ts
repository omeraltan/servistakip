import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServisIsUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.servisIs.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  baslamaTarihiInput: ElementFinder = element(by.css('input#servis-is-baslamaTarihi'));
  tamamlamaTarihiInput: ElementFinder = element(by.css('input#servis-is-tamamlamaTarihi'));
  sorumluSelect: ElementFinder = element(by.css('select#servis-is-sorumlu'));
  yapanSelect: ElementFinder = element(by.css('select#servis-is-yapan'));
  servisSelect: ElementFinder = element(by.css('select#servis-is-servis'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setBaslamaTarihiInput(baslamaTarihi) {
    await this.baslamaTarihiInput.sendKeys(baslamaTarihi);
  }

  async getBaslamaTarihiInput() {
    return this.baslamaTarihiInput.getAttribute('value');
  }

  async setTamamlamaTarihiInput(tamamlamaTarihi) {
    await this.tamamlamaTarihiInput.sendKeys(tamamlamaTarihi);
  }

  async getTamamlamaTarihiInput() {
    return this.tamamlamaTarihiInput.getAttribute('value');
  }

  async sorumluSelectLastOption() {
    await this.sorumluSelect.all(by.tagName('option')).last().click();
  }

  async sorumluSelectOption(option) {
    await this.sorumluSelect.sendKeys(option);
  }

  getSorumluSelect() {
    return this.sorumluSelect;
  }

  async getSorumluSelectedOption() {
    return this.sorumluSelect.element(by.css('option:checked')).getText();
  }

  async yapanSelectLastOption() {
    await this.yapanSelect.all(by.tagName('option')).last().click();
  }

  async yapanSelectOption(option) {
    await this.yapanSelect.sendKeys(option);
  }

  getYapanSelect() {
    return this.yapanSelect;
  }

  async getYapanSelectedOption() {
    return this.yapanSelect.element(by.css('option:checked')).getText();
  }

  async servisSelectLastOption() {
    await this.servisSelect.all(by.tagName('option')).last().click();
  }

  async servisSelectOption(option) {
    await this.servisSelect.sendKeys(option);
  }

  getServisSelect() {
    return this.servisSelect;
  }

  async getServisSelectedOption() {
    return this.servisSelect.element(by.css('option:checked')).getText();
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
    await this.setBaslamaTarihiInput('01-01-2001');
    await waitUntilDisplayed(this.saveButton);
    await this.setTamamlamaTarihiInput('01-01-2001');
    await this.sorumluSelectLastOption();
    await this.yapanSelectLastOption();
    await this.servisSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
